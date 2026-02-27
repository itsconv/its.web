function createModal(opt) {
    const $modal = $('#' + opt.id);

    $modal.find('.modal-title').text(opt.title);

    if (typeof opt.evt === 'function') opt.evt();

    //닫기
    $('.js-modal-close').on('click', function() {
        $modal.removeClass('is-open').attr('aria-hidden', 'true');
    });
}