(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('filteredQuestionsCtrl', filteredQuestionsCtrl);

    filteredQuestionsCtrl.$inject = ['$stateParams', 'questionsFromServer', 'questionsFactory'];

    function filteredQuestionsCtrl ($stateParams, questionsFromServer, questionsFactory) {

        /*jshint validthis:true*/
        var vm = this;
        var expand = [];
        vm.criteria = '';

        vm.questions = questionsFromServer;
        vm.tagName = $stateParams.tagName;
        vm.customerName = $stateParams.customerName;
        vm.checkCollapsed = checkCollapsed;
        vm.clearFilter = clearFilter;
        vm.checkInSet = checkInSet;
        vm.rateUp = rateUp;
        vm.editQuestion = editQuestion;
        vm.exportQuestion = exportQuestion;
        vm.isCollapsedAnswer = isCollapsedAnswer;

        function checkCollapsed(question) {
            expand = questionsFactory.checkCollapsed(expand, question);
        }

        function clearFilter() {
            questionsFactory.clearFilter(vm);
        }

        function checkInSet(key, obj) {
            return questionsFactory.checkInSet(key, obj);
        }

        function rateUp(questionUI) {
            questionsFactory.rateUp(questionUI);
        }

        function editQuestion(question) {
            questionsFactory.editQuestion(question, vm);
        }

        function exportQuestion(question) {
            questionsFactory.exportQuestion(question, vm);
        }

        function isCollapsedAnswer(question) {
            return containsInSet(expand, question);
        }
    }
})();