(function ($) {
  'use strict';

  window.codebase = {
    PAY_TYPE: [
      { code: '01', name: '일시납' },
      { code: '02', name: '분할납' }
    ],
    YN: [
      { code: 'Y', name: '예' },
      { code: 'N', name: '아니오' }
    ]
  };

  function findCodeLabel(baseKey, code) {
    const list = (baseKey && window.codebase[baseKey]) || [];
    const hit = list.find(function (item) { return item.code === code; });
    return hit ? hit.name : '';
  }

  function ioFilter($el, allowed) {
    const io = ($el.data('io') || '').toString().toUpperCase();
    return allowed.indexOf(io) >= 0;
  }

  function populateCombo($combo) {
    const baseKey = $combo.data('codebase') || $combo.data('option');
    const items = (baseKey && window.codebase[baseKey]) || [];
    const $menu = $combo.find('.cbui-combo-menu');
    $menu.empty();
    items.forEach(function (item) {
      const li = $('<li>')
        .attr('data-code', item.code)
        .attr('data-name', item.name)
        .text(item.name);
      $menu.append(li);
    });
  }

  function populateRadio($radio) {
    const baseKey = $radio.data('codebase') || $radio.data('option');
    const items = (baseKey && window.codebase[baseKey]) || [];
    const $options = $radio.find('.cbui-radio-options');
    $options.empty();
    items.forEach(function (item, idx) {
      const inputId = ($radio.attr('id') || 'cbui-radio') + '-' + idx;
      const radio = $('<label class="cbui-radio-item">')
        .append($('<input type="radio">').attr('name', $radio.data('field')).val(item.code).attr('id', inputId))
        .append($('<span>').text(item.name));
      $options.append(radio);
    });
  }

  function syncCheckbox($checkboxWrap, value) {
    const checked = value === 'Y';
    $checkboxWrap.find('.cbui-checkbox-value').val(checked ? 'Y' : 'N');
    $checkboxWrap.find('.cbui-checkbox-control').prop('checked', checked);
  }

  function syncCalendar($calendar, value) {
    $calendar.find('.cbui-calendar-value').val(value || '');
    $calendar.find('.cbui-calendar-display').val(value || '');
  }

  const cbui = {
    apply: function (dataObj, options) {
      const opts = options || {};
      const allowed = opts.io || ['O', 'IO'];
      $('[data-io]').each(function () {
        const $el = $(this);
        if (!ioFilter($el, allowed)) {
          return;
        }
        const field = $el.data('field');
        if (!field) {
          return;
        }
        const value = dataObj[field];
        if ($el.hasClass('cbui-combo')) {
          const label = findCodeLabel($el.data('codebase') || $el.data('option'), value) || value || '';
          $el.find('.cbui-combo-value').val(value || '');
          $el.find('.cbui-combo-label').text(label || '선택');
        } else if ($el.hasClass('cbui-checkbox')) {
          syncCheckbox($el, value ? value : 'N');
        } else if ($el.hasClass('cbui-radio')) {
          $el.find('.cbui-radio-value').val(value || '');
          $el.find('.cbui-radio-options input[type=radio]').each(function () {
            $(this).prop('checked', $(this).val() === value);
          });
        } else if ($el.hasClass('cbui-calendar')) {
          syncCalendar($el, value || '');
        } else if ($el.hasClass('cbui-textarea')) {
          $el.find('textarea').val(value || '');
        } else if ($el.hasClass('cbui-input')) {
          $el.find('input[type=text]').val(value || '');
        }
      });
    },
    collect: function (options) {
      const opts = options || {};
      const allowed = opts.io || ['I', 'IO'];
      const payload = {};
      $('[data-io]').each(function () {
        const $el = $(this);
        if (!ioFilter($el, allowed)) {
          return;
        }
        const field = $el.data('field');
        if (!field) {
          return;
        }
        let value = '';
        if ($el.hasClass('cbui-combo')) {
          value = $el.find('.cbui-combo-value').val();
        } else if ($el.hasClass('cbui-checkbox')) {
          value = $el.find('.cbui-checkbox-value').val();
        } else if ($el.hasClass('cbui-radio')) {
          value = $el.find('.cbui-radio-value').val();
        } else if ($el.hasClass('cbui-calendar')) {
          value = $el.find('.cbui-calendar-value').val();
        } else if ($el.hasClass('cbui-textarea')) {
          value = $el.find('textarea').val();
        } else if ($el.hasClass('cbui-input')) {
          value = $el.find('input[type=text]').val();
        }
        payload[field] = value;
      });
      return payload;
    },
    lookupName: function (baseKey, code) {
      return findCodeLabel(baseKey, code);
    }
  };

  window.cbui = cbui;

  $(function () {
    $('.cbui-combo').each(function () { populateCombo($(this)); });
    $('.cbui-radio').each(function () { populateRadio($(this)); });

    $('.cbui-checkbox').each(function () {
      const hiddenVal = $(this).find('.cbui-checkbox-value').val();
      syncCheckbox($(this), hiddenVal || 'N');
    });

    $('.cbui-calendar').each(function () {
      const $cal = $(this);
      const value = $cal.find('.cbui-calendar-value').val();
      syncCalendar($cal, value || '');
      if ($.fn.datepicker) {
        $cal.find('.cbui-calendar-display').datepicker({
          dateFormat: 'yy-mm-dd',
          onSelect: function (dateText) {
            $cal.find('.cbui-calendar-value').val(dateText);
          }
        });
      }
    });
  });

  $(document).on('click', '.cbui-combo-toggle', function (e) {
    e.preventDefault();
    $(this).siblings('.cbui-combo-menu').toggle();
  });

  $(document).on('click', '.cbui-combo-menu li', function () {
    const $li = $(this);
    const $combo = $li.closest('.cbui-combo');
    const code = $li.data('code');
    const name = $li.data('name') || $li.text();
    $combo.find('.cbui-combo-value').val(code);
    $combo.find('.cbui-combo-label').text(name);
    $combo.find('.cbui-combo-menu').hide();
  });

  $(document).on('change', '.cbui-checkbox-control', function () {
    const $wrap = $(this).closest('.cbui-checkbox');
    const val = $(this).is(':checked') ? 'Y' : 'N';
    syncCheckbox($wrap, val);
  });

  $(document).on('change', '.cbui-radio-options input[type=radio]', function () {
    const $wrap = $(this).closest('.cbui-radio');
    $wrap.find('.cbui-radio-value').val($(this).val());
  });

  $(document).on('change', '.cbui-calendar-display', function () {
    const $wrap = $(this).closest('.cbui-calendar');
    $wrap.find('.cbui-calendar-value').val($(this).val());
  });
})(jQuery);
