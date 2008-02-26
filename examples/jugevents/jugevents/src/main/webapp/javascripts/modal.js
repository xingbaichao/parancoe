


function require()
{
	aaa('mww').style.display = aaa('mbg').style.display = 'block';
	resize();
	
	if (window.attachEvent)
		window.attachEvent('onresize', resize);
	else if (window.addEventListener)
		window.addEventListener('resize', resize, false);
	else
		window.onresize = resize;
	
	
	if (document.all)
		document.documentElement.onscroll = resize;
		document.getElementById('commentr').focus();
}



function comeBack()
{
	$('mww').style.display = aaa('mbg').style.display = 'none';
	
	
	
	if (window.detachEvent)
		window.detachEvent('onresize', resize);
	else if (window.removeEventListener)
		window.removeEventListener('resize', resize, false);
	else
		window.onresize = null;
}


function resize()
{
	
	var left = window.XMLHttpRequest == null ? document.documentElement.scrollLeft : 0;
	var top = window.XMLHttpRequest == null ? document.documentElement.scrollTop : 0;
	var div =aaa('mww');
	
	div.style.left = Math.max((left + (GetWindowWidth() - div.offsetWidth) / 2), 0) + 'px';
	div.style.top = Math.max((top + (GetWindowHeight() - div.offsetHeight) / 2), 0) + 'px';
}

function GetWindowWidth()
{
	var width =
		document.documentElement && document.documentElement.clientWidth ||
		document.body && document.body.clientWidth ||
		document.body && document.body.parentNode && document.body.parentNode.clientWidth ||
		0;
		
	return width;
}

function GetWindowHeight()
{
    var height =
		document.documentElement && document.documentElement.clientHeight ||
		document.body && document.body.clientHeight ||
  		document.body && document.body.parentNode && document.body.parentNode.clientHeight ||
  		0;
  		
  	return height;
}

function aaa(id)
{
	return document.getElementById(id);
}
