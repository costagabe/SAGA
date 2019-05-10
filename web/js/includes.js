$( document ).ready(function(){
    $.ajax({
        url:"sistema/includes/menuLateral.html"
    }).done(function(data){
        $("#menuLateral").html(data);
        console.log(data);
    });

});