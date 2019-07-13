jQuery(document).ready(function(){

	$("#sidebar").mCustomScrollbar({
		theme: "minimal"
	});

	$('#sidebarCollapse').on('click', function () {
		$('#sidebar, #content').toggleClass('active');
		$('.collapse.in').toggleClass('in');
		$('a[aria-expanded=true]').attr('aria-expanded', 'false');
	});

	$('#sidebar ul li a').click(function() {
		$('#sidebar ul li a').removeClass('selected');
		$('.badge-light').removeClass('badge-light');
		$('.badge').addClass('badge-primary');
		$(this).addClass('selected');
		$(this).find(">:first-child").addClass('badge-light'); //народу не понравилось изменение цвета, там у бутстрапа есть анимация на изменение класса, она раздражает при переключении
	});

	$("button[name*='state']").click(function() {
		$('#filter-item').text($(this).html());
		// $("i[name*='filter-icon']").removeClass('d-none')
		$("button[name*='state']").removeClass('active');
		$(this).addClass('active');
	});


});


// alert('script ok!');
