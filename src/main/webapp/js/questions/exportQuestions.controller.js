(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('exportCtrl', exportCtrl);

    exportCtrl.$inject = ['$document', 'utility', 'questionsFactory'];

    function exportCtrl( $document, utility, questionsFactory) {
        /*jshint validthis:true*/
        var vm = this;

        vm.checkExport = checkExport;
        vm.clearList = clearList;

        function checkExport(key) {
            vm.exportLen = questionsFactory.getLength();
                return utility.ifExists(key);
            }

        function clearList() {
            var str_url = $document[0].URL;
            if (str_url.search('#/export') !== -1) {
                str_url = str_url.replace(new RegExp('/#/export', 'g'), '/#/home');
                $document[0].location.href = str_url;
            }
            utility.eraseSet('exportSet');
            vm.questionsForExport = questionsFactory.getExport();
            vm.exportLen = questionsFactory.getLength();
        }
    }
})();