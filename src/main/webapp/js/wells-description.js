var WellsList = {
		
	validate: function() {
		var id = $("#editWellId").val();
		var globalType = 3001;
		var exists = !!(
				$("[data-well-id]").map(function() {
					return $(this).data("well-id");
				})
				.filter(function() {
					return this == id;
				})
				.length
			);
		
		if (exists) {
			$("#editWellButton").removeAttr('disabled');
		} else {
			$("#editWellButton").attr('disabled', 'disabled');
		}
		
		return exists;
	},
	
	startEdit: function() {
		if (!WellsList.validate()) {
			return false;
		}
		
		window.location = '/fgi/descriptions/edit-card/' +  $("#editWellId").val();
		return false;
	}
};