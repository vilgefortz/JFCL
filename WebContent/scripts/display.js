$.urlParam = function(name) {
	var results = new RegExp('[\?&amp;]' + name + '=([^&amp;#]*)')
			.exec(window.location.href);
	if (results ===null ) return null;
	return results[1] || 0;
};
$(document).ready(function() {
	var id = $.urlParam('id');
	if (id === null) id = 1;
	$.post("Article", {
		id : id, action:"getArticle"
	}, function(data) {
		data = $.parseJSON(data);
		if (data.error === "") {
			$("#title").text(data.title);
			$("#content").html(data.content);
			$("#date").text(data.date);
		} else {
			alert(data.error);
			$("#content").html("<h1>404 article not found</h1>");
		}
	});
});