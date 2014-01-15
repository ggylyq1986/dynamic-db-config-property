$(document).ready(function() {
	$('#toggle').change(function() {
        $('td > input[type=checkbox]').prop('checked', $(this).prop('checked'))
    })
})
function clicked(id){
	$("#checkbox_" + id).prop('checked', true)
	$("#checkbox_" + id).prop('disabled', true)
}