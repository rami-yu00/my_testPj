<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="cbui" uri="http://boram/ui/cbui" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>CBUI 데모</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/widget.css" />
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/widget.js"></script>
</head>
<body>
<div class="cbui-section">
    <h2>검색 영역</h2>
    <div class="cbui-row">
        <span class="cbui-label">고객명</span>
        <cbui:input field="customerName" io="I" placeholder="고객명" />
        <span class="cbui-label">납입유형</span>
        <cbui:combo field="payType" io="I" option="PAY_TYPE" codebase="PAY_TYPE" />
        <span class="cbui-label">시작일</span>
        <cbui:calendar field="startDate" io="I" format="yyyy-MM-dd" placeholder="yyyy-mm-dd" />
        <span class="cbui-label">동의</span>
        <cbui:checkbox field="agree" io="IO" option="YN" label="동의" />
        <span class="cbui-label">사용 여부</span>
        <cbui:radio field="active" io="I" option="YN" codebase="YN" />
    </div>
</div>

<div class="cbui-section">
    <h2>목록 영역 (RealGrid 대체)</h2>
    <cbui:realgrid id="demoGrid" io="O" />
</div>

<div class="cbui-section">
    <h2>상세 영역</h2>
    <div class="cbui-row">
        <span class="cbui-label">고객명</span>
        <cbui:input field="detailName" io="O" />
        <span class="cbui-label">납입유형</span>
        <cbui:combo field="detailPayType" io="O" option="PAY_TYPE" codebase="PAY_TYPE" />
        <span class="cbui-label">시작일</span>
        <cbui:calendar field="detailStartDate" io="O" format="yyyy-MM-dd" />
    </div>
    <div class="cbui-row" style="margin-top:12px;">
        <span class="cbui-label">메모</span>
        <cbui:textarea field="memo" io="O" rows="4" placeholder="상세 메모" />
    </div>
</div>

<div class="cbui-section">
    <cbui:button id="btnSearch" text="조회" />
    <cbui:button id="btnReset" text="초기화" />
    <cbui:button id="btnSave" text="저장" />
</div>

<script>
    (function ($) {
        let gridRows = [];

        function buildRows(criteria) {
            const base = criteria.customerName || '고객';
            return [1, 2, 3].map(function (idx) {
                return {
                    customerName: base + idx,
                    payType: idx % 2 === 0 ? '02' : '01',
                    startDate: criteria.startDate || '2024-01-0' + idx,
                    active: idx % 2 === 0 ? 'Y' : 'N',
                    agree: criteria.agree || 'Y',
                    memo: '샘플 메모 ' + idx
                };
            });
        }

        function renderGrid(rows) {
            gridRows = rows;
            const $grid = $('#demoGrid');
            const $thead = $grid.find('thead');
            const $tbody = $grid.find('tbody');
            $thead.html('<tr><th>고객명</th><th>납입유형</th><th>시작일</th><th>상태</th></tr>');
            $tbody.empty();
            rows.forEach(function (row, idx) {
                const tr = $('<tr>')
                    .attr('data-index', idx)
                    .append($('<td>').text(row.customerName))
                    .append($('<td>').text(cbui.lookupName('PAY_TYPE', row.payType)))
                    .append($('<td>').text(row.startDate))
                    .append($('<td>').text(cbui.lookupName('YN', row.active)));
                $tbody.append(tr);
            });
        }

        $('#btnSearch').on('click', function () {
            const criteria = cbui.collect({ io: ['I', 'IO'] });
            const rows = buildRows(criteria);
            renderGrid(rows);
        });

        $('#btnReset').on('click', function () {
            cbui.apply({
                customerName: '',
                payType: '',
                startDate: '',
                agree: 'N',
                active: ''
            }, { io: ['I', 'IO'] });
            cbui.apply({
                detailName: '',
                detailPayType: '',
                detailStartDate: '',
                memo: ''
            }, { io: ['O', 'IO'] });
            renderGrid([]);
        });

        $('#btnSave').on('click', function () {
            const payload = cbui.collect({ io: ['I', 'IO'] });
            console.log('SAVE PAYLOAD', payload);
            alert('콘솔에서 payload를 확인하세요.');
        });

        $(document).on('click', '#demoGrid tbody tr', function () {
            const idx = $(this).data('index');
            const row = gridRows[idx];
            if (!row) {
                return;
            }
            cbui.apply({
                detailName: row.customerName,
                detailPayType: row.payType,
                detailStartDate: row.startDate,
                memo: row.memo
            }, { io: ['O', 'IO'] });
        });

        // 초기 렌더
        renderGrid([]);
    })(jQuery);
</script>
</body>
</html>
