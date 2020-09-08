

$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#tableForFiltering thead tr').clone(true).appendTo( '#tableForFiltering thead' );
    $('#tableForFiltering thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="'+title+'" />' );

        $( 'input', this ).on( 'keyup change', function () {
            if ( table.column(i).search() !== this.value ) {
                table
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );

    var table = $('#tableForFiltering').DataTable( {
        orderCellsTop: true,
        fixedHeader: true
    } );
} );

$('#tableForFiltering').DataTable( {
    buttons: [
        'excel'
    ]
} );

$('#tableForFiltering').DataTable( {
    buttons: [
        {
            extend: 'excel',
            text: 'Выгрузить в Excel',
            exportOptions: {
                modifier: {
                    page: 'current'
                }
            }
        }
    ]
} );