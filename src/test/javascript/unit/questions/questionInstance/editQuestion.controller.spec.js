'use strict';

describe("editQuestionInstanceCtrl", function () {

    var scope, ctrl, service, question, questionsCtrl;

    beforeEach(module('eightyFactories'));
    beforeEach(module('eightyControllers'));

    beforeEach(inject(function (modalData, questionsFactory, $rootScope, $controller) {
        scope = $rootScope.$new();
        service = questionsFactory;
        service = {
                  saveQuestionChanges: function(scope, question) {
                  },
                  deleteQuestion: function(scope, question) {
                  },
                  restoreQuestion: function(scope, question) {
                  },
                  loadCustomersByName: function(query) {
                  },
                  loadTagsByName: function(query) {
                  }
            };

        scope = {
                $close: function (result) {
                },
                $dismiss: function () {
                }
            };
        question = {question: 'fake question'};
        questionsCtrl = {};
        ctrl = $controller("editQuestionInstanceCtrl", {$scope: scope, questionsFactory: service, question: question, questionsCtrl: questionsCtrl});
        spyOn(scope, "$dismiss");
        spyOn(scope, "$close");
        spyOn(service, "saveQuestionChanges");
        spyOn(service, "deleteQuestion");
        spyOn(service, "restoreQuestion");
        spyOn(service, "loadCustomersByName").andReturn('customer 2');
        spyOn(service, "loadTagsByName").andReturn('tag 2');
    }));

    it("save question changes", function () {
        ctrl.saveQuestion();
        expect(service.saveQuestionChanges).toHaveBeenCalledWith(ctrl, question);
        expect(scope.$close).toHaveBeenCalledWith(question);
    });

    it("delete question", function () {
        ctrl.deleteQuestion();
        expect(service.deleteQuestion).toHaveBeenCalledWith(ctrl, question);
        expect(scope.$close).toHaveBeenCalledWith(question);
    });

    it("cancel edit question", function () {
        ctrl.cancelEdit();
        expect(scope.$dismiss).toHaveBeenCalled();
    });

    it("load customers", function () {
        expect(ctrl.loadCustomers('customer2')).toBe('customer 2');
    });

    it("load tags", function () {
        expect(ctrl.loadTags('tag2')).toBe('tag 2');
    });
});

