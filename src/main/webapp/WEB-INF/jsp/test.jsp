<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbui" uri="http://boram/ui/cbui" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>Widget Lab</title>
    <link rel="stylesheet" href="${ctx}/static/widget.css" />
    <script src="${ctx}/static/widget.js"></script>
</head>
<body>
<h1>실무형 위젯 샘플</h1>

<div class="section">
    <div class="section-title">검색 영역 (I / IO)</div>
    <div class="form-row">
        <label>고객명 (I)</label>
        <cbui:widget id="srchCustName" field="custName" io="I" label="" />
    </div>
    <div class="form-row">
        <label>납입구분 (IO)</label>
        <cbui:combo id="srchPayType" field="payType" io="IO" codebase="PAY_TYPE" label="" />
    </div>
    <div class="action-buttons">
        <button type="button" id="btnSearch" class="btn-primary">조회</button>
        <button type="button" id="btnReset" class="btn-secondary">초기화</button>
    </div>
</div>

<div class="section">
    <div class="section-title">목록 영역 (Grid 대체)</div>
    <div class="table-wrap">
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>고객명</th>
                <th>납입구분</th>
                <th>납입액</th>
                <th>메모</th>
            </tr>
            </thead>
            <tbody id="resultBody"></tbody>
        </table>
    </div>
</div>

<div class="section">
    <div class="section-title">상세 영역 (O / IO)</div>
    <div class="form-row">
        <label>계약번호 (O)</label>
        <cbui:widget id="detailContract" field="contractId" io="O" label="" />
    </div>
    <div class="form-row">
        <label>고객명 (O)</label>
        <cbui:widget id="detailCustName" field="custName" io="O" label="" />
    </div>
    <div class="form-row">
        <label>납입구분 (IO)</label>
        <cbui:combo id="detailPayType" field="payType" io="IO" codebase="PAY_TYPE" label="" />
    </div>
    <div class="form-row">
        <label>납입액 (IO)</label>
        <cbui:widget id="detailAmount" field="amount" io="IO" label="" />
    </div>
    <div class="form-row">
        <label>메모 (IO)</label>
        <cbui:widget id="detailMemo" field="memo" io="IO" label="" />
    </div>
    <div class="action-buttons">
        <button type="button" id="btnSave" class="btn-save">저장</button>
    </div>
</div>

<script>
    (function() {
        const state = {
            rows: []
        };

        const sampleRows = [
            { contractId: 'C-2024001', custName: '김고객', payType: '01', amount: '1,000,000', memo: '일시납 선납' },
            { contractId: 'C-2024002', custName: '박고객', payType: '02', amount: '250,000', memo: '분할납 5회차' },
            { contractId: 'C-2024003', custName: '이고객', payType: '02', amount: '300,000', memo: '분할납 3회차' },
            { contractId: 'C-2024004', custName: '최고객', payType: '01', amount: '2,500,000', memo: 'VIP 고객' }
        ];

        function renderTable(rows) {
            const body = document.getElementById('resultBody');
            body.innerHTML = '';
            rows.forEach((row, idx) => {
                const tr = document.createElement('tr');
                tr.dataset.index = idx;
                tr.innerHTML = `
                    <td>${idx + 1}</td>
                    <td>${row.custName}</td>
                    <td><span class="badge">${widget._resolveCodeLabel('PAY_TYPE', row.payType)}</span></td>
                    <td>${row.amount}</td>
                    <td>${row.memo}</td>
                `;
                tr.addEventListener('click', () => applyDetail(idx));
                body.appendChild(tr);
            });
        }

        function applyDetail(idx) {
            const row = state.rows[idx];
            if (!row) return;
            widget.apply(row, { io: ['O', 'IO'] });
        }

        function mockSearch(criteria) {
            return sampleRows.filter(item => {
                const matchName = !criteria.custName || item.custName.includes(criteria.custName);
                const matchPayType = !criteria.payType || item.payType === criteria.payType;
                return matchName && matchPayType;
            });
        }

        function handleSearch() {
            const criteria = widget.collect({ io: ['I', 'IO'] });
            const rows = mockSearch(criteria);
            state.rows = rows;
            renderTable(rows);
            if (rows.length > 0) {
                applyDetail(0);
            } else {
                widget.apply({ contractId: '', custName: '', payType: '', amount: '', memo: '' }, { io: ['O', 'IO'] });
            }
        }

        function handleReset() {
            widget.apply({ custName: '', payType: '' }, { io: ['I', 'IO'] });
            widget.apply({ contractId: '', custName: '', payType: '', amount: '', memo: '' }, { io: ['O', 'IO'] });
            state.rows = [];
            renderTable([]);
        }

        function handleSave() {
            const payload = widget.collect({ io: ['I', 'IO'] });
            console.log('Save payload', payload);
            alert('Payload collected! 콘솔을 확인하세요.');
        }

        document.getElementById('btnSearch').addEventListener('click', handleSearch);
        document.getElementById('btnReset').addEventListener('click', handleReset);
        document.getElementById('btnSave').addEventListener('click', handleSave);

        // initial load with full dataset
        handleSearch();
    })();
</script>
</body>
</html>
