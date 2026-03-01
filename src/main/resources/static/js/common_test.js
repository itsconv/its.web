function createModal(opt) {
    const $modal = $('#' + opt.id);
    const $dialog = $modal.find('.modal');

    $modal.find('.modal-title').text(opt.title);

    if (opt.width !== undefined && opt.width !== null && opt.width !== '') {
        const widthValue = (typeof opt.width === 'number') ? `${opt.width}px` : String(opt.width);
        $dialog.css('max-width', widthValue);
    }

    if (typeof opt.evt === 'function') opt.evt();

    //닫기
    $modal.find('.js-modal-close').off('click').on('click', function() {
        $modal.removeClass('is-open').attr('aria-hidden', 'true');
    });
}
