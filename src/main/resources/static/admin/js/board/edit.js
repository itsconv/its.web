window.KNOWN_EDITOR_IDS = window.KNOWN_EDITOR_IDS || [];
window.EDITOR_FILE_URL_MAP = window.EDITOR_FILE_URL_MAP || {};
window.REMOVE_MAPPED_IDS = window.REMOVE_MAPPED_IDS || [];

$(function () {
    pageInit.load();
    pageInit.button();
});

const pageInit = {
    load: function() {
        initEditor();
        initExistingFiles();
    },
    button: function() {
        // 썸네일
        $('.thumb-check').on('click', function() {
            const checked = $(this).is(':checked');
            const $row = $(this).closest('.bbs-file-row');
            const file = $row.find('input[type="file"]')[0].files[0];
            const hasExisting = !!$row.data('existing-detail-id');

            if(checked) {
                $('.thumb-check').not(this).prop('checked', false);
            }

            // 파일 미첨부시
            if(!file && !hasExisting) {
                alert("파일 첨부 후 체크해주세요.");
                $(this).prop('checked', false);
            }
        });

        // 파일 수정
        $('.bbs-file-edit-btn').on('click', function() {
            const $row = $(this).closest('.bbs-file-row');
            $row.find('input[type="file"]').trigger('click');
        });

        // 파일 선택 시 파일명 갱신
        $('.bbs-file-input').on('change', function() {
            const file = this.files[0];
            const $row = $(this).closest('.bbs-file-row');
            const existingMappedId = Number($row.data('existing-detail-id'));

            if (!file) return;

            if (existingMappedId) {
                addRemoveMappedId(existingMappedId);
                $row.removeData('existing-detail-id');
            }

            $row.find('.bbs-file-existing-name').text(file.name);
            $row.find('.bbs-file-existing-remove').hide();
            
            updateFileActionLabel($row);
        });

        // 기존 첨부파일 삭제
        $('.bbs-file-existing-remove').on('click', function() {
            const $row = $(this).closest('.bbs-file-row');
            const mappedId = Number($row.data('existing-detail-id'));
            
            if (!mappedId) return;
            if(!confirm("해당 파일을 삭제하시겠습니까?")) return;

            addRemoveMappedId(mappedId);
            
            $row.removeData('existing-detail-id');
            $row.find('.bbs-file-existing-name').text('');
            
            $(this).hide();
            updateFileActionLabel($row);

            const $thumb = $row.find("input[name='thumbnailOrder']");
            if ($thumb.is(':checked') && !$row.find('input[type="file"]')[0].files[0]) {
                $thumb.prop('checked', false);
            }
        });

        //저장
        $('#bbsSaveButton').on('click', function() {
            const $root = $('#bbsEditContent');
            const boardId = $root.data('board-id');
            const isCreate = $root.data('is-create');

            if (confirm("저장하시겠습니까?")) {
                const onSuccess = () => {
                    const path = $("#bbsEditContent").data('board-type');

                    alert("저장되었습니다.");

                    window.location.href=`/admin/bbs/${path}`;
                };

                if (isCreate) {
                    Executor.edit.save(onSuccess);
                    return;
                }

                Executor.edit.update(boardId, onSuccess);
            }
        });

        //취소
        $('#bbsCancelButton').on('click', function() {
            const $root = $('#bbsEditContent');
            const type = $root.data('board-type');
            const id = $root.data('board-id');
            const isCreate = $root.data('is-create');

            if (isCreate) {
                window.location.href=`/admin/bbs/${type}`;
                return;
            }

            window.location.href=`/admin/bbs/detail/${type}?id=${id}`;
        });
    }
}

let editor;
function initEditor() {
    if (editor) return;

    const $editorEl = $('#bbsEditor').get(0);
    const $contents = $('#contents').get(0);

    editor = new toastui.Editor({
        el: $editorEl,
        height: '380px',
        initialEditType: 'wysiwyg',
        previewStyle: 'vertical',
        toolbarItems: [
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task'],
            ['table', 'image', 'link'],
            ['code', 'codeblock']
        ],
        initialValue: $contents.value || '',
    hooks: {
            addImageBlobHook: async function(file, callback){
                try {
                    const frm = new FormData();
                    frm.append('file', file, file.name || 'image.png');

                    $.ajax({
                        url: '/api/file/temp',
                        type: 'POST',
                        data: frm,
                        processData: false,
                        contentType: false
                    })
                    .then(function(res) {
                        const detailId = res.data.detailId;
                        const url = res.data.url;

                        if (detailId) addKnownEditorId(detailId, url);

                        callback(url, detailId);
                    })
                    .catch(function(error){
                        console.log('Ajax error :', error);
                        alert(error.responseJSON.message);
                    });
                } catch (error) {
                    console.log("Failed to upload file:", error);
                    alert("이미지 업로드에 실패했습니다.");
                }
            }
        }
    });

    if (window.ResizeObserver) {
        const resizeObserver = new ResizeObserver(function () {
            const nextHeight = Math.max($editorEl.clientHeight, 380);
            editor.setHeight(`${nextHeight}px`);
        });

        resizeObserver.observe($editorEl);
    }
}

function initExistingFiles() {
    const $items = $('.bbs-file-existing-item');
    $('.bbs-file-existing-remove').hide();
    if ($items.length === 0) return;

    $items.each(function() {
        const $item = $(this);
        const sortOrder = Number($item.data('sort-order'));
        const detailId = $item.data('detail-id');
        const originName = $item.data('origin-name');
        const isThumbnail = $item.data('is-thumbnail') === 'Y';

        const $row = $(`.bbs-file-row[data-slot='${sortOrder}']`);
        if ($row.length === 0) return;

        $row.data('existing-detail-id', detailId);
        $row.find('.bbs-file-existing-name').text(originName);
        $row.find('.bbs-file-existing-remove').show();

        if (isThumbnail) {
            $row.find("input[name='thumbnailOrder']").prop('checked', true);
        }
        updateFileActionLabel($row);
    });
    
    const $editorItem = $('.bbs-editor-files');
    
    $editorItem.each(function() {
        const detailId = $(this).data('detail-id');
        const fileUrl = $(this).data('file-url');
        
        addKnownEditorId(detailId, fileUrl);
    });
}

function updateFileActionLabel($row) {
    const hasExisting = !!$row.data('existing-detail-id');
    const hasFile = !!$row.find('input[type="file"]')[0].files[0];
    const label = (hasExisting || hasFile) ? '수정' : '파일선택';
    $row.find('.bbs-file-edit-btn').text(label);
}

function addRemoveMappedId(mappedId) {
    if (!window.REMOVE_MAPPED_IDS.includes(mappedId)) {
        window.REMOVE_MAPPED_IDS.push(mappedId);
    }
}

function addKnownEditorId(detailId, fileUrl) {
    const id = Number(detailId);

    if (!Number.isFinite(id) || id <= 0) return;
    if (!window.KNOWN_EDITOR_IDS.includes(id)) {
        window.KNOWN_EDITOR_IDS.push(id);
    }

    const normalizedUrl = normalizeEditorFileUrl(fileUrl);
    if (normalizedUrl) {
        window.EDITOR_FILE_URL_MAP[normalizedUrl] = id;
    }
}

function getCurrentEditorDetailIds() {
    const html = editor.getHTML();
    const doc = new DOMParser().parseFromString(html, 'text/html');

    return [...doc.querySelectorAll('img')]
        .map((img) => window.EDITOR_FILE_URL_MAP[normalizeEditorFileUrl(img.getAttribute('src'))])
        .filter((id) => Number.isFinite(id) && id > 0);
}

function getRemovedEditorDetailIds(currentDetailIds) {
    const currentIds = Array.isArray(currentDetailIds) ? currentDetailIds : getCurrentEditorDetailIds();

    return window.KNOWN_EDITOR_IDS.filter((id) => !currentIds.includes(id));
}

window.getCurrentEditorDetailIds = getCurrentEditorDetailIds;
window.getRemovedEditorDetailIds = getRemovedEditorDetailIds;

function normalizeEditorFileUrl(fileUrl) {
    if (!fileUrl) return null;

    try {
        return new URL(fileUrl, window.location.origin).toString();
    } catch (error) {
        console.log('Invalid editor file url:', fileUrl, error);
        return null;
    }
}
