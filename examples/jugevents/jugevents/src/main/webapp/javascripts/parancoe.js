function Parancoe() {
    function Util() {
        this.initDWR = function () {            
            useLoadingMessage();
            dwr.engine.reverseAjax = true;
        };
        
        this.disableFormElement = function (elementId) {
            $(elementId).disable();
        }

        this.enableFormElement = function (elementId) {
            $(elementId).enable();
        }
        
    }

    this.util = new Util();
}

var parancoe = new Parancoe();

function useLoadingMessage(message) {
  var loadingMessage;
  if (message) loadingMessage = message;
  else loadingMessage = "Loading...";

  dwr.engine.setPreHook(function() {
    var messageZone = $('messageZone');
    if (!messageZone) {
      messageZone = document.createElement('div');
      messageZone.setAttribute('id', 'messageZone');
                   
      document.body.appendChild(messageZone);
      var text = document.createTextNode(loadingMessage);
      messageZone.appendChild(text);
    }
    else {
      messageZone.innerHTML = loadingMessage;
      messageZone.style.visibility = 'visible';
    }
  });

  dwr.engine.setPostHook(function() {
    $('messageZone').style.visibility = 'hidden';
  });
}