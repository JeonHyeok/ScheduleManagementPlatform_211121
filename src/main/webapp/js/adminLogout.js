$(function() {
	$("#logoutAdmin").on("click", function() {
		var answer = confirm("로그아웃을 하시겠습니까?");
		var ret = "";

		if (answer == true) {
			document.location.href="/main";
		}
	});
});