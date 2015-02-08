$(document).ready(function () {
	$.post ("Article",{action:"getArticles"}, function (data) {
		data=$.parseJSON(data);
		$.each(data, function (key, value) {
			value=$.parseJSON(value);
			$("#articles").append("<li><a href='index.jsp?m=article&a=display&id=" + value.id + "'>" + value.date +  " " + value.title + "</a></li>");
		});
	});
});