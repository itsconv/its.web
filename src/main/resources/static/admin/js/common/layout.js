$(function() {
  const $sidebar = $('.sidebar');
  const $collapseBtn = $('#collapseBtn');

  if ($sidebar.length > 0 && $collapseBtn.length > 0) {
    const isCollapsed = localStorage.getItem('sidebar_collapsed') === 'true';
    if (isCollapsed) {
      $sidebar.addClass('collapsed');
      $collapseBtn.text('▶');
      $collapseBtn.attr('aria-label', '사이드바 열기');
    }

    $collapseBtn.on('click', function() {
      $sidebar.toggleClass('collapsed');
      const collapsed = $sidebar.hasClass('collapsed');
      
      localStorage.setItem('sidebar_collapsed', collapsed);
      
      if (collapsed) {
        $(this).text('▶');
        $(this).attr('aria-label', '사이드바 열기');
      } else {
        $(this).text('◀');
        $(this).attr('aria-label', '사이드바 접기');
      }
    });
  }
});