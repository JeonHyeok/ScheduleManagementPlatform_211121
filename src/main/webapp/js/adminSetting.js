$(function() {
	btnSettingDialog = $("#btnSetform").dialog({
		autoOpen : false,
		height : 850,
		width : 1200,
		modal : true,
		buttons : {
			"저장" : function() {
				btnSettingForm.trigger("submit");
			},
			"취소" : function() {
				btnSettingDialog.dialog("close");
			}
		},
		close : function() {
			
		}
	});
	btnSettingForm = btnSettingDialog.find("form").on("submit",
			function(event) {
				var valid = true;
				
				if (valid) {
					btnSettingDialog.dialog("close");
				}
				return valid;
			});
	
	$("#settingsBtnAdmin").on("click", function() {
		btnSettingDialog.dialog("open");
	});
	
});

function check(box) {
	   if(box.checked == true && box.value == "studio") {
	   $("#category1").hide();
	   } else if(box.checked == true && box.value == "rental") {
		   $("#category2").hide();
	   } else if(box.checked == true && box.value == "consulting") {
		   $("#category3").hide();
	   } else if(box.checked == true && box.value == "faq") {
		   $("#category4").hide();
	   }
	   
	   if (box.checked == false && box.value == "studio") {
	       $("#category1").show();
	   } else if (box.checked == false && box.value == "rental") {
		   $("#category2").show();
	   } else if (box.checked == false && box.value == "consulting") {
		   $("#category3").show();
	   } else if (box.checked == false && box.value == "faq") {
		   $("#category4").show();
	   }
	}