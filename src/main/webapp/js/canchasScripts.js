$(document).ready(function () {

    $("#nuevaCanchaBtn").show();
    $('#editarCanchaBtn').hide();

    var table = datatableInit("myTable");



    $("#myTable tbody").on('click', 'tr', function () {
        var id = table.row(this).data()[0];

        if ($(this).hasClass('table-active')) {
            $(this).removeClass('table-active');

            $("#nuevaCanchaBtn").show();
            $('#editarCanchaBtn').hide();


            $("#id").val('');
            $("#nombre").val('');
            $("#descripcion").val('');



        } else {
            table.$('tr.table-active').removeClass('table-active');
            $(this).addClass('table-active');
            
            $("#nuevaCanchaBtn").hide();
            $('#editarCanchaBtn').show();

            $.ajax({
                url: "CanchaGetOne",
                type: 'POST',
                data: {id: id},
                dataType: 'json',
                success: function (data) {

                    if (data !== null) {
                        $("#id").val(data.id);
                        $("#nombre").val(data.nombre);
                        $("#descripcion").val(data.descripcion);
                    } else {
                        $("#id").val('');
                        $("#nombre").val('');
                        $("#descripcion").val('');
                    }

                }
            });


        }




    });

});

