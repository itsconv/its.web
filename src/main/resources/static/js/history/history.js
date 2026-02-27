$(function() {
    pageInit.load();
    pageInit.button();
});

const pageInit = {
    load: function() {
        setModal();

        getHistoryPeriod();
    },
    button: function() {
        //연혁 추가 modal 열기
        $('#btnAddHistory').off('click').on('click', function() {
            modalBtnView().create();
        });
    }
}

function modalBtnView($el = null, targetId = null) {
    const $modal = $('#historyEditModal');
    const $modify = $('#btnConfirmModify');
    const $create = $('#btnConfirmCreate');
    
    function create() {
        //초기화
        $modal.find('.td-input input').each(function() {
            $(this).val('')
        });
        $modal.addClass('is-open').attr('aria-hidden', 'false');

        $modify.hide();
        $create.show();
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
        
        $('#historyEditModal')
            .addClass('is-open')
            .attr('aria-hidden', 'false')
            .attr('data-history-id', targetId);

        $modify.show();
        $create.hide();
    }

    $('#startPeriod').focus();

    return {create, modify}
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

    })
    .on('click.history.top', '.history-delete', function() { //삭제
        const $this = $(this);
        const msg = "해당 연혁을 삭제하시겠습니까?";
        const targetId = $this.closest('td').data('history-id');

        if(confirm(msg)) {
            Executor.period.remove(targetId, function(res) {
                alert(res.message);
                getHistoryPeriod();
            });
        }
    });
}