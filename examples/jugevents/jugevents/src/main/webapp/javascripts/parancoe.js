function Parancoe() {
    function Util() {
        this.initDWR = function () {
            dwr.util.useLoadingMessage();
            dwr.engine.reverseAjax = true;
        };
    }

    this.util = new Util();
}

var parancoe = new Parancoe();
