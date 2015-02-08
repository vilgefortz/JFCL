<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#editor {
	height: 400px;
	position: relative;
}
</style>
<div class="row">
	<div class="col-md-8 editor">
		<div id="editor">function foo(items) { var x = "All this is
			syntax highlighted"; return x; }</div>
	</div>
	<div id="sidebar" class="col=md-4 sidebar">
		<pre id="code">
			
		</pre>
	</div>
</div>
<script src="scripts/lib/ace/src-noconflict/ace.js"
	type="text/javascript" charset="utf-8"></script>
<script>
	var editNotification = false;
	var editor = ace.edit("editor");
	editor.setTheme("ace/theme/monokai");
	editor.getSession().on('change', function() {
		if (!editNotification) {
			editNotification = true;
			window.setTimeout(function() {
				$.post ("Gateway?action=generateJson",
						{data:editor.getSession().getValue()},
						function (value) {
							$("#code").text(value);
						});
				editNotification=false;
			},4000);
		}
	});
</script>

