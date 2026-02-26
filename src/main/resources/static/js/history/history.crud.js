const Executor = (function() {
    function getTopParam() {
        const start = $('#startPeriod').val();
        const end = $('#endPeriod').val();

        return {start, end};
    }

    const top = {
        modify(id) {
            const paylod = {...getTopParam(), id};

            $.ajax({
                url: `/api/history/${id}/modify`,
                type: 'POST',
                dataType: 'JSON',
            })
            .then(function(res) {
                
            })
            .catch(function(error){
                alert('에러발생!', error);
            });
        },
        insert() {

        }
    };

    const detail = {
        modify() {
            console.log("Detail 수정처리");
        }
    }

    return {top, detail};
})();