// 추가한 함수
// 아이디 중복
$('#IdDupCheck').click(function() {
	let userId = $('#inputUserId').val();
	if (userId.trim().length == 0) {
		alert('뭐라도 입력해라');
	} else {
		console.log(userId);
		$.ajax({
			url : "idCheck",
			type : "post",
			async : false,
			data : {
				sdy : userId
			},
			dataType : 'json',
			success : function(result) {
				if (result == 0) {
					$('#IdDupWarning').css("display", "none");
					$('#IdDupWarning').css("display", "block");
					// !important사용하려면 attr로 변경해야함
					$('#IdDupCheck').attr('style', "background-color : #369b00 !important");
					$('#IdDupCheck').css("border", "none");
					$('#IdDupCheck').css("color", "white");
					$('label[for="IdLabelWarning"]').css('color', '#369b00');
					$('label[for="IdLabelWarning"]').text('아이디 사용가능!');
					$('#DupCheck').attr('value','Check');
					$('#inputUserId').attr('readonly', true);
				} else {
					$('#IdDupWarning').css("display", "none");
					$('#IdDupWarning').css("display", "block");
					$('#IdDupCheck').attr('style', "background-color : red !important")
					.css("border", "none")
					.css("color", "white");
					$('label[for="IdLabelWarning"]').css('color', 'rgb(255 0 0)');
					$('label[for="IdLabelWarning"]').text('아이디 중복 다시입력');
					$('#DupCheck').attr('value','UnCheck');
					$('#inputUserId').val('');
					$('#inputUserId').focus();
				}
			},
			error : function() {
				alert("서버요청실패");
			}
		});
	}
});

// 아이디 재입력
function reInput() {
	$('#IdDupWarning').css("display", "none");
	$('#inputUserId').attr('readonly', false);
	$('#DupCheck').attr('value','UnCheck');
	$('#inputUserId').val('');
	$('#IdDupCheck').attr('style', "background-color : white !important")
	.css("border", "solid 1.2px #369b00")
	.css("color", "black");
}

// 비밀번호 확인
function checkPw() {
	let pw = document.getElementById('registPW').value;
	if (pw.length < 4) {
		$('#PwDupWarning').css("display", "none");
		$('#PwDupWarning').css("display", "block");
		$('label[for="PwLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="PwLabelWarning"]').text('비밀번호는 4자이상으로 작성해주세요');
		document.getElementById('registPW').value = '';
	}
	if (pw != '' && document.getElementById('checkPW').value != '') {
		if (pw == document.getElementById('checkPW').value) {
			$('#PwDupWarning').css("display", "none");
			$('#PwDupWarning').css("display", "block");
			$('label[for="PwLabelWarning"]').css('color', '#369b00');
			$('label[for="PwLabelWarning"]').text('비밀번호일치!');
		} else {
			$('#PwDupWarning').css("display", "none");
			$('#PwDupWarning').css("display", "block");
			$('label[for="PwLabelWarning"]').css('color', 'rgb(255 0 0)');
			$('label[for="PwLabelWarning"]').text('비밀번호 불일치 다시입력');
			document.getElementById('checkPW').value = '';
		}
	}
}

// 비밀번호 변경 확인
function checkNewPw() {
	let newPw = document.getElementById('newRegistPw').value;
	if (newPw.length < 4) {
		$('#NewPwDupWarning').css("display", "none");
		$('#NewPwDupWarning').css("display", "block");
		$('label[for="NewPwLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="NewPwLabelWarning"]').text('비밀번호는 4자이상으로 작성해주세요');
		document.getElementById('newRegistPw').value = '';
	}
	if (newPw != '' && document.getElementById('newCheckPw').value != '') {
		if (newPw == document.getElementById('newCheckPw').value) {
			$('#NewPwDupWarning').css("display", "none");
			$('#NewPwDupWarning').css("display", "block");
			$('label[for="NewPwLabelWarning"]').css('color', '#369b00');
			$('label[for="NewPwLabelWarning"]').text('비밀번호일치!');
		} else {
			$('#NewPwDupWarning').css("display", "none");
			$('#NewPwDupWarning').css("display", "block");
			$('label[for="NewPwLabelWarning"]').css('color', 'rgb(255 0 0)');
			$('label[for="NewPwLabelWarning"]').text('비밀번호 불일치 다시입력');
			document.getElementById('newCheckPw').value = '';
		}
	}
}


// 비밀번호 변경 form확인
function changePwAjax() {
	let moveSuccessOrFail = true;
	let userPw = $('#nowPw').val();
	$.ajax({
		url : "pwCheck",
		type : "post",
		async : false,
		data : {
			reqPw : userPw
		},
		dataType : 'json',
		success : function(result) {
			if (result == 1) {
				moveSuccessOrFail = false;
				$('#NewPwDupWarning').css("display", "none");
				document.getElementById('nowPw').value = '';
				document.getElementById('newRegistPw').value = '';
				document.getElementById('newCheckPw').value = '';
				alert("현재비밀번호 틀림");
			}
		},
		error : function() {
			alert("서버요청실패");
		}
	});
	return moveSuccessOrFail;
}

function changePw() {
	let nowPw2 = $('#nowPw').val();
	let newCheckPw2 = $('#newCheckPw').val();
	if (nowPw2 == newCheckPw2) {
	$('#NewPwDupWarning').css("display", "none");
		$('#NewPwDupWarning').css("display", "block");
		$('label[for="NewPwLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="NewPwLabelWarning"]').text('현재 비밀번호와 새 비밀번호를 다르게 입력하세요');
		document.getElementById('newRegistPw').value = '';
		document.getElementById('newCheckPw').value = '';
		return false;
	}
	return changePwAjax();
}

// 로그인 form확인
function loginAjax() {
	let loginSuccessOrFail = true;
	let userId = $('#inputLoginId').val();
	let userPw = $('#inputLoginPw').val();
	$.ajax({
		url : "loginCheck",
		type : "post",
		async : false,
		data : {
			reqId : userId,
			reqPw : userPw
		},
		dataType : 'json',
		//끊기는 구간
		success : function(result) {
			if (result == 0) {
				loginSuccessOrFail = false;
				$('#loginInputDiv').css("display", "block");
				$('label[for="loginInputLabel"]').css('color', 'rgb(255 0 0)');
				$('label[for="loginInputLabel"]').text('아이디 혹은 비밀번호가 틀렸습니다');
				document.getElementById('inputLoginPw').value = '';
			}
		},
		error : function() {
			alert("서버요청실패");
		}
	});
	return loginSuccessOrFail;
}

function loginCheckInfo() {
	return loginAjax();
}


// 주민번호 앞자리 6자 다입력하면 바로 뒷자리입력하게 커서변경됨
function moveNext(obj, length, nextObj) {
	if (obj.value.length == length) {
		nextObj.focus();
	}
}

function formCheck(obj) {
	if (obj.rn1.value.trim().length !== 6) {
		$('#RnDupWarning').css("display", "none");
		$('#RnDupWarning').css("display", "block");
		$('label[for="RnLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="RnLabelWarning"]').text('주민등록번호 앞자리는 6자리');
		obj.rn1.value = "";
		obj.rn1.focus();
		return false;
	}
	// NaN = Not a Number, Number()는 인수를 숫자로변경
	if (isNaN(Number(obj.rn1.value))) {
		$('#RnDupWarning').css("display", "none");
		$('#RnDupWarning').css("display", "block");
		$('label[for="RnLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="RnLabelWarning"]').text('주민등록번호 앞자리는 숫자만 입력');
		obj.rn1.value = "";
		obj.rn1.focus();
		return false;
	}
	if (obj.rn2.value.trim().length !== 7) {
		$('#RnDupWarning').css("display", "none");
		$('#RnDupWarning').css("display", "block");
		$('label[for="RnLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="RnLabelWarning"]').text('주민등록번호 뒷자리는 7자리');
		obj.rn2.value = "";
		obj.rn2.focus();
		return false;
	}
	if (isNaN(Number(obj.rn2.value))) {
		$('#RnDupWarning').css("display", "none");
		$('#RnDupWarning').css("display", "block");
		$('label[for="RnLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="RnLabelWarning"]').text('주민번호 뒷자리는 숫자만 입력');
		obj.rn2.value = "";
		obj.rn2.focus();
		return false;
	}
	if (obj.DupCheck.value!="Check"){
		$('#RnDupWarning').css("display", "none");
		$('#RnDupWarning').css("display", "block");
		$('label[for="RnLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="RnLabelWarning"]').text('아이디중복체크');
		return false;
	}
	//주민번호 유효성검사
	/*
	rnCheck = obj.rn1.value.trim() + obj.rn2.value.trim();
	sum = 0;
	for (i = 0; i < 12; i++) {
		sum += rnCheck.charAt(i) * (i < 8 ? i + 2 : i - 6);
	}
	result = (11 - sum % 11);
	if (result != rnCheck.charAt(12)) {
		$('#RnDupWarning').css("display", "none");
		$('#RnDupWarning').css("display", "block");
		$('label[for="RnLabelWarning"]').css('color', 'rgb(255 0 0)');
		$('label[for="RnLabelWarning"]').text('주민등록 번호가 올바르지 않습니다');
		obj.rn2.value = "";
		obj.rn2.focus();
		return false;
	}*/
	
	return checkRn();
}

function formCheckFindId(obj) {
	if (obj.rn1.value.trim().length !== 6) {
		$('#SRnDupWarningId').css("display", "none");
		$('#SRnDupWarningId').css("display", "block");
		$('label[for="SRnLabelWarningId"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningId"]').text('주민등록번호 앞자리는 6자리');
		obj.rn1.value = "";
		obj.rn1.focus();
		return false;
	}
	// NaN = Not a Number, Number()는 인수를 숫자로변경
	if (isNaN(Number(obj.rn1.value))) {
		$('#SRnDupWarningId').css("display", "none");
		$('#SRnDupWarningId').css("display", "block");
		$('label[for="SRnLabelWarningId"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningId"]').text('주민등록번호 앞자리는 숫자만 입력');
		obj.rn1.value = "";
		obj.rn1.focus();
		return false;
	}
	if (obj.rn2.value.trim().length !== 7) {
		$('#SRnDupWarningId').css("display", "none");
		$('#SRnDupWarningId').css("display", "block");
		$('label[for="SRnLabelWarningId"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningId"]').text('주민등록번호 뒷자리는 7자리');
		obj.rn2.value = "";
		obj.rn2.focus();
		return false;
	}
	if (isNaN(Number(obj.rn2.value))) {
		$('#SRnDupWarningId').css("display", "none");
		$('#SRnDupWarningId').css("display", "block");
		$('label[for="SRnLabelWarningId"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningId"]').text('주민번호 뒷자리는 숫자만 입력');
		obj.rn2.value = "";
		obj.rn2.focus();
		return false;
	}
	
	return true;
}

function formCheckFindPw(obj) {
	if (obj.rn1.value.trim().length !== 6) {
		$('#SRnDupWarningPw').css("display", "none");
		$('#SRnDupWarningPw').css("display", "block");
		$('label[for="SRnLabelWarningPw"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningPw"]').text('주민등록번호 앞자리는 6자리');
		obj.rn1.value = "";
		obj.rn1.focus();
		return false;
	}
	// NaN = Not a Number, Number()는 인수를 숫자로변경
	if (isNaN(Number(obj.rn1.value))) {
		$('#SRnDupWarningPw').css("display", "none");
		$('#SRnDupWarningPw').css("display", "block");
		$('label[for="SRnLabelWarningPw"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningPw"]').text('주민등록번호 앞자리는 숫자만 입력');
		obj.rn1.value = "";
		obj.rn1.focus();
		return false;
	}
	if (obj.rn2.value.trim().length !== 7) {
		$('#SRnDupWarningPw').css("display", "none");
		$('#SRnDupWarningPw').css("display", "block");
		$('label[for="SRnLabelWarningPw"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningPw"]').text('주민등록번호 뒷자리는 7자리');
		obj.rn2.value = "";
		obj.rn2.focus();
		return false;
	}
	if (isNaN(Number(obj.rn2.value))) {
		$('#SRnDupWarningPw').css("display", "none");
		$('#SRnDupWarningPw').css("display", "block");
		$('label[for="SRnLabelWarningPw"]').css('color', 'rgb(255 0 0)');
		$('label[for="SRnLabelWarningPw"]').text('주민번호 뒷자리는 숫자만 입력');
		obj.rn2.value = "";
		obj.rn2.focus();
		return false;
	}
	
	return true;
}

function checkRn(){
	// 주민등록번호 중복체크
	let trueOrFalse = true;
	$.ajax({
		url : "rnCheck",
		type : "post",
		async : false,
		data : {
			checkRegistNumber : rnCheck
		},
		dataType : 'json',
		success : function(result) {
			if (result==true) {
				trueOrFalse = false;
				$('#RnDupWarning').css("display", "none");
				$('#RnDupWarning').css("display", "block");
				$('label[for="RnLabelWarning"]').css('color', 'rgb(255 0 0)');
				$('label[for="RnLabelWarning"]').text('해당주민등록번호로 회원가입된 아이디있음');
			}
		},
		error : function() {
			trueOrFalse = false;
			alert("서버요청실패");
		}
	});
	return trueOrFalse;
}


function warningHideAndShow() {
	document.querySelector('.myFields').style.display = "none";
}

// 기존
(function($) {

	var $window = $(window),
		$body = $('body'),
		$wrapper = $('#wrapper'),
		$header = $('#header'),
		$footer = $('#footer'),
		$main = $('#main'),
		$main_articles = $main.children('article');

	// Breakpoints.
	breakpoints({
		xlarge: ['1281px', '1680px'],
		large: ['981px', '1280px'],
		medium: ['737px', '980px'],
		small: ['481px', '736px'],
		xsmall: ['361px', '480px'],
		xxsmall: [null, '360px']
	});

	// Play initial animations on page load.
	$window.on('load', function() {
		window.setTimeout(function() {
			$body.removeClass('is-preload');
		}, 100);
	});

	// Fix: Flexbox min-height bug on IE.
	if (browser.name == 'ie') {

		var flexboxFixTimeoutId;

		$window.on('resize.flexbox-fix', function() {

			clearTimeout(flexboxFixTimeoutId);

			flexboxFixTimeoutId = setTimeout(function() {

				if ($wrapper.prop('scrollHeight') > $window.height())
					$wrapper.css('height', 'auto');
				else
					$wrapper.css('height', '100vh');

			}, 250);

		}).triggerHandler('resize.flexbox-fix');

	}

	// Nav.
	var $nav = $header.children('nav'),
		$nav_li = $nav.find('li');

	// Add "middle" alignment classes if we're dealing with an even number of items.
	if ($nav_li.length % 2 == 0) {

		$nav.addClass('use-middle');
		$nav_li.eq(($nav_li.length / 2)).addClass('is-middle');

	}

	// Main.
	var delay = 325,
		locked = false;

	// Methods.
	$main._show = function(id, initial) {

		var $article = $main_articles.filter('#' + id);

		// No such article? Bail.
		if ($article.length == 0)
			return;

		// Handle lock.

		// Already locked? Speed through "show" steps w/o delays.
		if (locked || (typeof initial != 'undefined' && initial === true)) {

			// Mark as switching.
			$body.addClass('is-switching');

			// Mark as visible.
			$body.addClass('is-article-visible');

			// Deactivate all articles (just in case one's already active).
			$main_articles.removeClass('active');

			// Hide header, footer.
			$header.hide();
			$footer.hide();

			// Show main, article.
			$main.show();
			$article.show();

			// Activate article.
			$article.addClass('active');

			// Unlock.
			locked = false;

			// Unmark as switching.
			setTimeout(function() {
				$body.removeClass('is-switching');
			}, (initial ? 1000 : 0));

			return;

		}

		// Lock.
		locked = true;

		// Article already visible? Just swap articles.
		if ($body.hasClass('is-article-visible')) {

			// Deactivate current article.
			var $currentArticle = $main_articles.filter('.active');

			$currentArticle.removeClass('active');

			// Show article.
			setTimeout(function() {

				// Hide current article.
				$currentArticle.hide();

				// Show article.
				$article.show();

				// Activate article.
				setTimeout(function() {

					$article.addClass('active');

					// Window stuff.
					$window
						.scrollTop(0)
						.triggerHandler('resize.flexbox-fix');

					// Unlock.
					setTimeout(function() {
						locked = false;
					}, delay);

				}, 25);

			}, delay);

		}

		// Otherwise, handle as normal.
		else {

			// Mark as visible.
			$body
				.addClass('is-article-visible');

			// Show article.
			setTimeout(function() {

				// Hide header, footer.
				$header.hide();
				$footer.hide();

				// Show main, article.
				$main.show();
				$article.show();

				// Activate article.
				setTimeout(function() {

					$article.addClass('active');

					// Window stuff.
					$window
						.scrollTop(0)
						.triggerHandler('resize.flexbox-fix');

					// Unlock.
					setTimeout(function() {
						locked = false;
					}, delay);

				}, 25);

			}, delay);

		}

	};

	$main._hide = function(addState) {

		var $article = $main_articles.filter('.active');

		// Article not visible? Bail.
		if (!$body.hasClass('is-article-visible'))
			return;

		// Add state?
		if (typeof addState != 'undefined'
			&& addState === true)
			history.pushState(null, null, '#');

		// Handle lock.

		// Already locked? Speed through "hide" steps w/o delays.
		if (locked) {

			// Mark as switching.
			$body.addClass('is-switching');

			// Deactivate article.
			$article.removeClass('active');

			// Hide article, main.
			$article.hide();
			$main.hide();

			// Show footer, header.
			$footer.show();
			$header.show();

			// Unmark as visible.
			$body.removeClass('is-article-visible');

			// Unlock.
			locked = false;

			// Unmark as switching.
			$body.removeClass('is-switching');

			// Window stuff.
			$window
				.scrollTop(0)
				.triggerHandler('resize.flexbox-fix');

			return;

		}

		// Lock.
		locked = true;

		// Deactivate article.
		$article.removeClass('active');

		// Hide article.
		setTimeout(function() {

			// Hide article, main.
			$article.hide();
			$main.hide();

			// Show footer, header.
			$footer.show();
			$header.show();

			// Unmark as visible.
			setTimeout(function() {

				$body.removeClass('is-article-visible');

				// Window stuff.
				$window
					.scrollTop(0)
					.triggerHandler('resize.flexbox-fix');

				// Unlock.
				setTimeout(function() {
					locked = false;
				}, delay);

			}, 25);

		}, delay);


	};

	// Articles.
	$main_articles.each(function() {

		var $this = $(this);

		// Close.
		$('<div class="close">Close</div>')
			.appendTo($this)
			.on('click', function() {
				location.hash = '';
				hideMenu();
			});

		// Prevent clicks from inside article from bubbling.
		$this.on('click', function(event) {
			event.stopPropagation();
		});

	});

	// Events.
	$body.on('click', function(event) {

		// Article visible? Hide.
		if ($body.hasClass('is-article-visible'))
			$main._hide(true);

	});

	$window.on('keyup', function(event) {

		switch (event.keyCode) {

			case 27:

				// Article visible? Hide.
				if ($body.hasClass('is-article-visible'))
					$main._hide(true);

				break;

			default:
				break;

		}

	});

	$window.on('hashchange', function(event) {

		// Empty hash?
		if (location.hash == ''
			|| location.hash == '#') {

			// Prevent default.
			event.preventDefault();
			event.stopPropagation();

			// Hide.
			$main._hide();

		}

		// Otherwise, check for a matching article.
		else if ($main_articles.filter(location.hash).length > 0) {

			// Prevent default.
			event.preventDefault();
			event.stopPropagation();

			// Show article.
			$main._show(location.hash.substr(1));

		}

	});

	// Scroll restoration.
	// This prevents the page from scrolling back to the top on a hashchange.
	if ('scrollRestoration' in history)
		history.scrollRestoration = 'manual';
	else {

		var oldScrollPos = 0,
			scrollPos = 0,
			$htmlbody = $('html,body');

		$window
			.on('scroll', function() {

				oldScrollPos = scrollPos;
				scrollPos = $htmlbody.scrollTop();

			})
			.on('hashchange', function() {
				$window.scrollTop(oldScrollPos);
			});

	}

	// Initialize.

	// Hide main, articles.
	$main.hide();
	$main_articles.hide();

	// Initial article.
	if (location.hash != ''
		&& location.hash != '#')
		$window.on('load', function() {
			$main._show(location.hash.substr(1), true);
		});

})(jQuery);