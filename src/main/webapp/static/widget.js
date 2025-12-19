window.codebase = window.codebase || {
    PAY_TYPE: [
        { code: "01", name: "일시납" },
        { code: "02", name: "분할납" }
    ]
};

const widget = (() => {
    const defaultApplyIO = ["O", "IO"];
    const defaultCollectIO = ["I", "IO"];

    function normalizeIO(list, fallback) {
        return Array.isArray(list) && list.length ? list : fallback;
    }

    function getWidgets(ioList) {
        return Array.from(document.querySelectorAll('[data-field][data-io]')).filter(el =>
            ioList.includes((el.dataset.io || "").toUpperCase())
        );
    }

    function resolveCodeLabel(codebaseName, code) {
        if (!codebaseName || code === undefined || code === null) {
            return "";
        }
        const entries = window.codebase && window.codebase[codebaseName];
        if (!entries) {
            return "";
        }
        const found = entries.find(item => item.code === String(code));
        return found ? found.name : "";
    }

    function findLabel(el) {
        return el.querySelector('.cb-combo-label') || el.querySelector('.cb-widget-label');
    }

    function updateWidget(el, value) {
        const hidden = el.querySelector('input[type="hidden"]');
        if (hidden) {
            hidden.value = value !== undefined && value !== null ? value : "";
        }
        const labelEl = findLabel(el);
        if (labelEl) {
            if (el.dataset.codebase) {
                labelEl.textContent = resolveCodeLabel(el.dataset.codebase, value);
            } else {
                labelEl.textContent = value !== undefined && value !== null ? String(value) : "";
            }
        }
    }

    function apply(dataObj, opts) {
        const ioList = normalizeIO(opts && opts.io, defaultApplyIO).map(v => v.toUpperCase());
        const rows = getWidgets(ioList);
        rows.forEach(el => {
            const field = el.dataset.field;
            if (!field) return;
            if (!(field in dataObj)) return;
            updateWidget(el, dataObj[field]);
        });
    }

    function collect(opts) {
        const ioList = normalizeIO(opts && opts.io, defaultCollectIO).map(v => v.toUpperCase());
        const rows = getWidgets(ioList);
        const payload = {};
        rows.forEach(el => {
            const field = el.dataset.field;
            if (!field) return;
            const hidden = el.querySelector('input[type="hidden"]');
            let value = hidden ? hidden.value : "";
            if (!value) {
                const labelEl = findLabel(el);
                if (labelEl) {
                    value = labelEl.textContent.trim();
                }
            }
            payload[field] = value;
        });
        return payload;
    }

    function initEditableWidgets() {
        document.querySelectorAll('.cb-widget:not(.cb-combo) .cb-widget-label').forEach(label => {
            label.setAttribute('contenteditable', 'true');
            label.addEventListener('input', () => {
                const wrapper = label.closest('.cb-widget');
                const hidden = wrapper && wrapper.querySelector('input[type="hidden"]');
                if (hidden) {
                    hidden.value = label.textContent.trim();
                }
            });
        });
    }

    function buildOptionList(combo) {
        const codebaseName = combo.dataset.codebase;
        const options = window.codebase && window.codebase[codebaseName];
        if (!options) {
            return null;
        }
        const list = document.createElement('ul');
        list.className = 'cb-combo-options';
        options.forEach(item => {
            const li = document.createElement('li');
            li.textContent = item.name;
            li.dataset.value = item.code;
            li.addEventListener('click', e => {
                e.stopPropagation();
                updateWidget(combo, item.code);
                const labelEl = findLabel(combo);
                if (labelEl) {
                    labelEl.textContent = item.name;
                }
                closeAllCombos();
            });
            list.appendChild(li);
        });
        return list;
    }

    function closeAllCombos(except) {
        document.querySelectorAll('.cb-combo-options').forEach(list => {
            if (!except || !except.contains(list)) {
                list.remove();
            }
        });
    }

    function toggleCombo(combo) {
        const existing = combo.querySelector('.cb-combo-options');
        if (existing) {
            existing.remove();
            return;
        }
        closeAllCombos(combo);
        const list = buildOptionList(combo);
        if (list) {
            combo.appendChild(list);
        }
    }

    function bindComboEvents() {
        document.querySelectorAll('.cb-combo').forEach(combo => {
            combo.addEventListener('click', e => {
                e.stopPropagation();
                toggleCombo(combo);
            });
        });
        document.addEventListener('click', () => closeAllCombos());
    }

    document.addEventListener('DOMContentLoaded', () => {
        initEditableWidgets();
        bindComboEvents();
    });

    return {
        apply,
        collect,
        _updateWidget: updateWidget,
        _resolveCodeLabel: resolveCodeLabel
    };
})();
