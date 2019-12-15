

//var current_fs, next_fs, previous_fs;



$(document).ready(function () {
    //用parent啥存cuurent應該更好,有空研究

    $("#ticketset").hide();
    $("#dataset").hide();
    $("#payset").hide();
    $("#concertdata").hide();


    $("#order_b").click(function () {

        $("#datetab").removeClass("active");
        $("#tickettab").addClass("active");


        $("#orderset").hide();
        $("#ticketset").show();
        $("#concertdata").show();
    });

    $("#ticket_b").click(function () {

        $("#tickettab").removeClass("active");
        $("#datatab").addClass("active");

        $("#ticketset").hide();
        $("#dataset").show();


    });
    $("#data_b").click(function () {

        $("#datatab").removeClass("active");
        $("#paytab").addClass("active");

        $("#dataset").hide();
        $("#payset").show();

    });
    $("#pay_b").click(function () {
        alert("開始繳費");

    });

    //及時反映剩下票數
    $.ajax({
        url: "",
        method: 'get',
        error: function (err) {
            console.log(err);
        },
        success: function (data) {

            //那個時區的票數
            $("#quentitynow").html("剩下"+data.quentity+"張");
            

        }
    });








});

