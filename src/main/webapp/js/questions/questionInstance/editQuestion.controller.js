(function(){
    'use strict';
    angular
        .module('eightyControllers')
        .controller('editQuestionInstanceCtrl', editQuestionInstance);

    editQuestionInstance.$inject = ['$scope', 'modalData', 'questionsFactory', 'question', 'questionsCtrl'];
    function editQuestionInstance ($scope, modalData, questionsFactory, question, questionsCtrl) {
        /*jshint validthis:true*/
        var vm = this;

        vm.question = {
            question: question.question,
            answer: question.answer,
            tags: question.tags,
            customers: question.customers
        };

        vm.saveQuestion = saveQuestion;
        vm.deleteQuestion = delQuestion;
        vm.cancelEdit = cancelEditQuestion;
        vm.loadCustomers = loadCustomers;
        vm.loadTags = loadTags;
        vm.questions = questionsCtrl.questions;
        vm.questionsForExport = questionsCtrl.questionsForExport;
        vm.selectedTags = questionsCtrl.selectedTags;
        vm.clearTags = questionsCtrl.clearTags;

        vm.shouldBeOpen = modalData.getShouldBeOpen();

        function delQuestion() {
            questionsFactory.deleteQuestion(vm, question);
            $scope.$close(vm.question);
        }

        function saveQuestion() {
            questionsFactory.saveQuestionChanges(vm, question);
            $scope.$close(vm.question);
        }

        function cancelEditQuestion() {
            $scope.$dismiss();
        }

        function loadCustomers(query) {
            return questionsFactory.loadCustomersByName(query);
        }

        function loadTags(query) {
            return questionsFactory.loadTagsByName(query);
        }
    }
})();

