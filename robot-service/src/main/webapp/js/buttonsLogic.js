$(document).ready(function() {
$('#refresh').click(function(){
    $.ajax({
        method: "GET"
        , url: "http://localhost:8080/reset"
        , dataType: 'jsonp'
        , jsonpCallback: "myFunc",
        success: function (data) {
            console.log(data);
        }
    });
});
});
