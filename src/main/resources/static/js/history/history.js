$(function() {
    pageInit.load();
    pageInit.button();
});

const pageInit = {
    load: function() {
        setModal();

        getHistoryPeriod();

        detailBtnEvt();
    },
    button: function() {
        //연혁 추가 modal 열기
        $('#btnAddHistory').off('click').on('click', function() {
            modalBtnView().create();
        });
    }
}

function modalBtnView($el = null, targetId = null) {
    const $editModal = $('#historyEditModal');
    const $detailModal = $('#historyDetailModal');
    const $modify = $('#btnConfirmModify');
    const $create = $('#btnConfirmCreate');
    
    function create() {
        //초기화
        $editModal.find('.td-input input').each(function() {
            $(this).val('')
        });
        $editModal.addClass('is-open').attr('aria-hidden', 'false');

        $modify.hide();
        $create.show();

        $('#startPeriod').focus();
    }

    function modify() {
        $('#startPeriod').val(
            $el
                .closest('td')
                .siblings('.start-period')
                .text()
        );

        $('#endPeriod').val(
            $el
                .closest('td')
                .siblings('.end-period')
                .text()
        );
        
        $editModal
            .addClass('is-open')
            .attr('aria-hidden', 'false')
            .attr('data-history-id', targetId);

        $modify.show();
        $create.hide();

        $('#startPeriod').focus();
    }

    function detail() {
        $detailModal
            .addClass('is-open')
            .attr('aria-hidden', 'false')
            .attr('data-period-id', targetId);

        // 연혁 리스트
        getDetailValue(targetId);
    }

    return {create, modify, detail}
}

function getDetailValue(periodId) {
    const callback = (res) => {
        renderDetailList(res.data);
    }

    Executor.year.getList(periodId, (result) => callback(result));
}

function renderDetailList(list) {
    const $tbl = $('#tblHistoryDetail');

    if (list && list.length) {
        //초기화
        $tbl.empty();

        let inner = `<tbody>`;
        
        for (const parent of list) {
            const items = parent.items || [];

            if (!items.length) {
                inner += `
                    <tr data-year-id="${parent.yearId}">
                        <td class="td-detail-year">${parent.year}</td>
                        <td class="td-detail-add">
                            <div class="detail-add-row">
                                <button type="button" class="add-btn year-action add-item-btn">
                                    <span class="icon"><img src="/asset/img/plus.gif" alt="" /></span>
                                    <span class="action-text">추가</span>
                                </button>
                                <button type="button" class="btn-delete-year delete-year-btn" data-year-id="${parent.yearId}" aria-label="연도 삭제">
                                    <span class="trash-icon" aria-hidden="true"></span>
                                    <span class="action-text">연도삭제</span>
                                </button>
                            </div>
                        </td>
                    </tr>
                `;
                continue;
            }

            items.forEach((item, index) => {
                inner += `<tr data-year-id="${parent.yearId}" data-item-id="${item.itemId}">`;

                //첫번째
                if (index === 0) {
                    inner += `
                        <td class="td-detail-year" rowspan="${items.length + 1}">${parent.year}</td>
                    `
                }

                inner += `
                    <td class="td-detail-item">
                        <div class="detail-item-row">
                            <input type="text" value="${item.content}" disabled/>
                            <button type="button" class="btn-item-edit">편집</button>
                            <button type="button" class="btn-item-save is-hidden">저장</button>
                            <button type="button" class="btn-item-cancel-edit is-hidden">취소</button>
                            <button type="button" class="btn-icon-delete delete-item-btn" aria-label="삭제">
                                <span class="sr-only">삭제</span>
                            </button>
                        </div>
                    </td>
                `;
                
                inner += `</tr>`;
            });

            inner += `
                <tr data-year-id="${parent.yearId}" class="row-item-add">
                    <td class="td-detail-add">
                        <button type="button" class="add-btn year-action add-item-btn">
                            <span class="icon"><img src="/asset/img/plus.gif" alt="" /></span>
                            <span class="action-text">추가</span>
                        </button>
                    </td>
                </tr>
            `;
        };

        inner += `</tbody>`;

        $tbl.append(inner);
    }
}

function setModal() {
    createModal({
        id: 'historyEditModal',
        title: '연혁관리',
        evt: function() {
            const $modal = $('#historyEditModal');

            //수정완료
            $('#btnConfirmModify').off('click').on('click', function() {    
                const targetId = $modal.attr('data-history-id');
                const msg = "수정하시겠습니까?";

                if(confirm(msg)) {
                    Executor.period.modify(targetId,(result) => callback(result));
                }
            });

            //저장
            $('#btnConfirmCreate').off('click').on('click', function() {
                const msg = "저장하시겠습니까?";

                if(confirm(msg)) {
                    Executor.period.create((result) => callback(result));
                }
            });

            function callback(result) {
                alert(result.message);

                //닫기
                $modal.removeClass('is-open').attr('aria-hidden','true');

                getHistoryPeriod();
            }
        }
    });

   createModal({
        id: 'historyDetailModal',
        title: '연혁관리 상세',
        width: '790px',
        evt: function() {
            const $modal = $('#historyDetailModal');

            //연도추가
            $('#btnAddHistoryYear').off('click').on('click', function() {
                const year = prompt("연도를 입력해주세요.");
                const periodId = $modal.data('period-id');
                const callback = () => {
                    getDetailValue(periodId);
                }

                if (year == null) return;

                Executor.year.create({year, periodId},callback);
            });
        }
    });
}

async function getHistoryPeriod() {
    try {
        const result = await $.ajax({
            url: `/api/history/period`,
            type: `GET`,
            dataType: `JSON`,
        })
    
        renderHistoryTable(result);
    } catch (error) {
        console.log("Error : ", error);  
    }
}

function renderHistoryTable(res) {
    const resList = res.data;
    const $head = $('#dataTableHead');
    const $body = $('#dataTableBody');

    $head.empty();
    $body.empty();

    $head.append(
        `<tr>
            <td>시작구간</td>
            <td>종료구간</td>
            <td>기능</td>
        </tr>
        `
    );

    for (const period of resList) {
        $body.append(
            `<tr>
                <td align="center" class="start-period">${period.startPeriod}</td>
                <td align="center" class="end-period">${period.endPeriod}</td>
                <td align="center" data-history-id="${period.id}">
                    <button class="h18 red_s history-edit">수정</button>
                    <button class="h18 blue_s history-detail-open">상세</button>
                    <button class="h18 blue_s history-delete">삭제</button>
                </td>
            </tr>
            `
        );
    }

    tableRowEvt();
}

function tableRowEvt() {
    $('#dataTableBody')
        .off('click.history.top')
        .on('click.history.top', '.history-edit',function() { //수정 Modal 열기
            const $this = $(this);
            const targetId = $this.closest('td').data('history-id');

            modalBtnView($this, targetId).modify();
        })
        .on('click.history.top', '.history-detail-open', function() { //상세
            const $this = $(this);
            const targetId = $this.closest('td').data('history-id');

            modalBtnView($this, targetId).detail();
        })
        .on('click.history.top', '.history-delete', function() { //삭제
            const $this = $(this);
            const targetId = $this.closest('td').data('history-id');
            const msg = "해당 연혁을 삭제하시겠습니까?";

            if(confirm(msg)) {
                Executor.period.remove(targetId, function(res) {
                    alert(res.message);
                    getHistoryPeriod();
                });
            }
        });
}

function detailBtnEvt() {
    const $detailTbl = $('#tblHistoryDetail');
    const $modal = $('#historyDetailModal');
    
    const callback = (result) => {
        alert(result.message);

        const periodId = $modal.data('period-id');
        
        getDetailValue(periodId);
    }

    //버튼 이벤트 위임
    $detailTbl
    .off('.historyDetail')
    .on('click.historyDetail', '.add-item-btn', function() { //연혁추가
        const $this = $(this);
        const yearId = $this.closest('tr').data('year-id');

        //단일 입력모드 강제: 다른 편집/신규입력 행 먼저 정리
        closeAllInlineModes();
        appendInput(yearId);
    })
    .on('click.historyDetail', '.delete-item-btn', function() { //연혁삭제
        const $this = $(this);
        const itemId = $this.closest('tr').data('item-id');

        if (confirm("해당 연혁을 삭제하시겠습니까?")) {
            Executor.item.remove(itemId, (result) => callback(result));
        }
    })
    .on('click.historyDetail', '.delete-year-btn', function() { //연도삭제
        const $this = $(this);
        const yearId = $this.data('year-id');

        if (confirm("해당 연도를 삭제하시겠습니까?")) {
            Executor.year.remove(yearId, (result) => callback(result));
        }
    })
    .on('click.historyDetail', '.btn-item-edit', function() { //연도편집
        const $this = $(this);
        const $row = $this.closest('.detail-item-row');
        const $input = $row.find('input[type="text"]');

        //다른 편집행이 있다면 편집 해제(원본 복원)
        closeOtherEditingRows($row);

        //원본 저장
        $input.data('originValue', $input.val());

        //입력모드 전환
        $input.prop('disabled', false).focus();
        $row.addClass('is-editing');

        //Button view: 편집모드에서는 저장/취소만 노출
        $row.find('.btn-item-save').removeClass('is-hidden');
        $row.find('.btn-item-cancel-edit').removeClass('is-hidden');
        $row.find('.btn-item-edit').addClass('is-hidden');
        $row.find('.btn-icon-delete').addClass('is-hidden');
    })
    .on('click.historyDetail', '.btn-item-save', function() { //저장
        const $this = $(this);
        const itemId = $this.closest('tr').data('item-id');
        const yearId = $this.closest('tr').data('year-id');
        const content = $this
            .siblings('input[type="text"]')
            .val()
            .trim();

        if (confirm("저장하시겠습니까?")) {
            if (itemId) {
                Executor.item.modify({itemId, content}, (result) => callback(result));
            } else {
                Executor.item.create({yearId, content}, (result) => callback(result));
            }
        }
    })
    .on('click.historyDetail', '.btn-item-cancel-edit', function() { //편집취소
        const $row = $(this).closest('.detail-item-row');
        exitEditMode($row, true);
    })
    .on('click.historyDetail', '.btn-item-cancel-create', function() { //신규취소
        const $this = $(this);
        const $cell = $this.closest('td');
        const backupHtml = $cell.data('backup-html');

        if (backupHtml) {
            $cell
                .removeClass('td-detail-item')
                .addClass('td-detail-add')
                .html(backupHtml);
        }
    });

    function appendInput(yearId) {
        const $targetCell = $modal.find(`tr[data-year-id="${yearId}"] .td-detail-add`).first();
        const backupHtml = $targetCell.html();

        const innerHtml = `
            <div class="detail-item-row is-creating">
                <input type="text" />
                <button type="button" class="btn-item-edit is-hidden">편집</button>
                <button type="button" class="btn-item-save">저장</button>
                <button type="button" class="btn-item-cancel-create">취소</button>
                <button type="button" class="btn-icon-delete delete-item-btn is-hidden" aria-label="삭제">
                    <span class="sr-only">삭제</span>
                </button>
            </div>
        `;

        //이미 신규 입력행이 떠있으면 중복 생성하지 않음
        if ($targetCell.find('.detail-item-row.is-creating').length > 0) return;

        $targetCell
            .data('backup-html', backupHtml)
            .removeClass('td-detail-add')
            .addClass('td-detail-item')
            .html(innerHtml);
    }

    function closeOtherEditingRows($currentRow) {
        $detailTbl.find('.detail-item-row.is-editing').each(function() {
            const $row = $(this);
            if ($currentRow && $row.is($currentRow)) return;
            exitEditMode($row, true);
        });
    }

    function closeAllInlineModes() {
        //기존 편집행 정리
        closeOtherEditingRows(null);

        //신규 입력행 정리(원상복구)
        $detailTbl.find('.detail-item-row.is-creating').each(function() {
            const $cell = $(this).closest('td');
            const backupHtml = $cell.data('backup-html');

            if (backupHtml) {
                $cell
                    .removeClass('td-detail-item')
                    .addClass('td-detail-add')
                    .html(backupHtml);
            }
        });
    }

    function exitEditMode($row, restoreOriginal) {
        const $input = $row.find('input[type="text"]');
        const origin = $input.data('originValue');

        if (restoreOriginal && origin !== undefined) {
            $input.val(origin);
        }

        $input.prop('disabled', true);
        $row.removeClass('is-editing');

        //편집모드 해제: 편집/삭제 노출, 저장/취소 숨김
        $row.find('.btn-item-save').addClass('is-hidden');
        $row.find('.btn-item-cancel-edit').addClass('is-hidden');
        $row.find('.btn-item-edit').removeClass('is-hidden');
        $row.find('.btn-icon-delete').removeClass('is-hidden');
    }
}
