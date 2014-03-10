$(document).ready(function() {
	$('#toggle').change(function() {
        $('td > input[type=checkbox]').prop('checked', $(this).prop('checked'))
    })
})
