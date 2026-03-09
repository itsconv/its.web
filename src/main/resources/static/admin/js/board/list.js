$(function() {
    pageInit.load();
    pageInit.button();
});

const pageInit = {
    load: function() {
        setModal();
    },
    button: function() {
        const $listModal = $('#boardListModal');
        const $orderModal = $('#boardOrderModal');
        const $tableBody = $('#dataTableBody');

        // 페이징 클래스 위임
        $('.board-pagination-fragment')
        .off('click.boardBtnControl')
        .on('click.boardBtnControl', '.admin-page-control' , function() {  //페이징
            const $this = $(this);
        
            if ($this.attr('disabled')) return;
        
            const $form = $('#' + $this.data('target'));
            const $searchForm = $('#frmBbsData');
            const page = $this.data('page');
            const keyword = $searchForm.find('.search-keyword').val();
            const searchType = $searchForm.find('.search-type').val();
            const size = $searchForm.find('input[name="size"]').val();
        
            $form.find('input[name="page"]').val(page);
            $form.find('input[name="size"]').val(size);
            $form.find('input[name="keyword"]').val(keyword);
            $form.find('input[name="searchType"]').val(searchType);
        
            $form.submit();
        })
        .on('click.boardBtnControl', '.btn-delete-list', function() { //선택삭제
            const chk = checkValidation();

            if (!chk.isChecked) {
                alert("삭제 할 게시물을 선택해주세요.");
                return;
            }

            const msg = "삭제 하시겠습니까?";

            if(confirm(msg)) {
                Executor.list.remove(chk.targetArr, (result) => {
                    alert(result.message);

                    location.reload();
                });
            }            
        })
        .on('click.boardBtnControl', '.btn-copy-list', function() { //선택복사
            const chk = checkValidation();

            if (!chk.isChecked){
                alert("복사 할 게시물을 선택해주세요.");
                return;
            }

            //선택된 게시물
            $listModal.data('target-id', chk.targetArr);

            modalBtnView().copy();
        })
        .on('click.boardBtnControl', '.btn-move-board', function() { //선택이동
            const chk = checkValidation();

            if (!chk.isChecked){
                alert("이동 할 게시물을 선택해주세요.");
                return;
            }
            
            //선택된 게시물
            $listModal.data('target-id', chk.targetArr);

            modalBtnView().move();
        })
        .on('click.boardBtnControl', '.btn-update-order', function() { //순서변경
            const $checked = $tableBody.find('.check-item:checked');
            const isChecked = $checked.length > 1;

            if(!isChecked) {
                alert("순서변경 할 게시물을 2개 이상 선택해주세요.");
                return;
            }

            renderCheckedOrderData($checked);

            $orderModal.addClass('is-open').attr('aria-hidden', 'false');
        })
        .on('click.boardBtnControl', '.btn-write-board', function() {  //글쓰기
            const type = `${BOARD_TYPE}`.toLowerCase();
            window.location.href = `/admin/bbs/edit/${type}`;
        })

        // 테이블 클래스 위임
        $('.table-wrap')
        .off('change.tableCheck')
        .on('change.tableCheck', '#checkAllItem', function() {  //게시글 선택
            const checked = $(this).is(':checked');
            $tableBody.find('td input.check-item').prop('checked', checked);
        })
        .on('change.tableCheck', '.check-item', function() {
            const total = $tableBody.find('tr').length;
            const checekd = $tableBody.find('.check-item:checked').length;

            $('#checkAllItem').prop('checked', total > 0 && total === checekd);
        })
        .off('click.tableTitle')
        .on('click.tableTitle', '.board-title', function() {    // 게시판 상세
            const id = $(this).data('board-id');

            window.location.href=`/admin/bbs/detail/${id}`;
        });

        function checkValidation() {
            const targetArr = [];
            const $checked = $tableBody.find('.check-item:checked');
            const isChecked = $checked.length > 0;

            $checked.each(function() {
                targetArr.push($(this).data('board-id'));
            });

            return {targetArr, isChecked};
        }

        function renderCheckedOrderData($items) {
            const $table = $('#tblManageOrder');

            $table.find('tbody').empty();

            $items.each(function() {
                const id = $(this).data('board-id');
                const idx = $(this).data('board-idx');
                const title = $(this).data('board-title');
                const order = $(this).data('board-order');
                const innerHtml = `
                    <tr data-board-id=${id}>
                        <td>${idx}</td>
                        <td>${title}</td>
                        <td class="td-input">
                            <input type="text" class="request-order" value="${order}" />
                        </td>
                    </tr>
                `;

                $table.find('tbody').append(innerHtml);
            });
        }
    }
}

function setModal() {
    createModal({
        id: 'boardListModal',
        title: '게시판 관리',
        evt: function() {
            const $modal = $('#boardListModal');

            //이동
            $('#btnConfirmMove').off('click').on('click', function() {    
                const targetId = $modal.data('target-id');
                const targetType = $("#boardSelect").val();
                const msg = "이동 하시겠습니까?";

                if (targetType == '') {
                    alert("대상 게시판을 선택해주세요.");
                    return;
                }

                if (targetType === BOARD_TYPE) {
                    alert("다른 게시판을 선택해주세요.");
                    return;
                }

                if(confirm(msg)) {
                    Executor.list.move({targetId, targetType},(result) => callback(result));
                }
            });

            //복사
            $('#btnConfirmCopy').off('click').on('click', function() {    
                const targetId = $modal.data('target-id');
                const targetType = $("#boardSelect").val();
                const msg = "복사 하시겠습니까?";

                if (targetType == '') {
                    alert("대상 게시판을 선택해주세요.");
                    return;
                }

                if(confirm(msg)) {
                    Executor.list.copy({targetId, targetType},(result) => callback(result));
                }
            });


            function callback(result) {
                alert(result.message);

                //닫기
                $modal.removeClass('is-open').attr('aria-hidden','true');

                location.reload();
            }
        }
    });
    createModal({
        id: 'boardOrderModal',
        title: '순서 변경',
        evt: function() {
            const $modal = $('#boardOrderModal');

            //저장
            $('#btnConfirmOrder').off('click').on('click', function() {
                const reqArr = [];
                const $table = $('#tblManageOrder');
                const msg = "변경 하시겠습니까?";
                let isDuplicate = false;
                const orderSet = new Set();

                $table.find('tbody tr').each(function() {
                    const id = $(this).data('board-id');
                    const order = $(this).find('.request-order').val();

                    // 순서중복 체크
                    if (orderSet.has(order)) {
                        isDuplicate = true;
                        return false;
                    }

                    orderSet.add(order);
                    reqArr.push({id, order});
                });

                if (isDuplicate) {
                    alert("순서 값이 중복되었습니다.");
                    return;
                }

                if(confirm(msg)) {
                    Executor.list.order(reqArr,(result) => callback(result));
                }
            });

            function callback(result) {
                alert(result.message);

                //닫기
                $modal.removeClass('is-open').attr('aria-hidden','true');

                location.reload();
            }
        }
    });
}

function modalBtnView() {
    const $modal = $('#boardListModal');
    const $move = $('#btnConfirmMove');
    const $copy = $('#btnConfirmCopy');
    
    $modal.addClass('is-open').attr('aria-hidden', 'false');

    function move() {
        $move.show();
        $copy.hide();
    }

    function copy() {
        $move.hide();
        $copy.show();
    }

    return {move, copy}
}

