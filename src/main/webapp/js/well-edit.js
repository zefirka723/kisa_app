WellEdit = {
    removeRow: function(node) {
        $(node).closest('tr').remove();
        return false;
    },

    addRow: function(templateRowId) {
        var $templateRow = $("#" + templateRowId);
        let $existingRows = $templateRow.nextAll('tr');
        var $row = $templateRow.clone();

        var index;
        if ($existingRows.length) {
            index = Math.max.apply(null, (
                $existingRows.map(function() {
                    return $(this).data('index');
                })
            )) + 1;
        } else {
            index = 0;
        }

        $row
            .show()
            .removeAttr('id')
            .data('index', index)
            .find('input, select, textarea').each(function() {
            var $e = $(this);
            var id = $e.data('name');
            if (id) {
                $e.attr('name', id.replace('[]', '[' + index + ']'));
            }
        });
        $templateRow.parent().append($row);
        return false;
    }

};