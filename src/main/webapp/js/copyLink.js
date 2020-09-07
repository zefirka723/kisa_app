function copyLink(elementId) {
		var copyText = document.getElementById(elementId);
		copyText.select();
		document.execCommand("copy");
		alert("Ссылка скопирована. Теперь вы можете использовать её в браузере или в Проводнике");
}