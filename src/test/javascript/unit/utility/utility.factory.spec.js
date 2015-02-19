'use strict';

describe('service', function () {

    beforeEach(module('eightyApp'));

    it('check the existence of utility', inject(function (utility) {
        expect(utility).toBeDefined();
    }));

    describe('utility', function() {
        var factory;
        var key, object1, object2, arr;

        beforeEach(function() {
            key = 'key';
            object1 = {id: 1, string: 'string 1', number: 100};
            object2 = {id: 2, string: 'string 2', number: 200};
            arr = [object2, object1, object2, object2];
        });

        beforeEach(inject(function(utility) {
            factory = utility;
        }));

        it('ifExists', function() {
            expect(factory.ifExists(key)).toBeFalsy();
            factory.saveInSet(key, object1);
            factory.saveInSet(key, object2);
            expect(factory.ifExists(key)).toBeTruthy();
            expect(factory.getSet(key)).not.toBeNull();
            factory.eraseSet();
        });

        it('saveInSet', function() {
            spyOn(factory, 'saveInSet');
            factory.saveInSet(key, object1);
            expect(factory.saveInSet).toHaveBeenCalled();
            expect(factory.saveInSet).toHaveBeenCalledWith(key, object1);
        });

        it('containsInSet', function() {
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object1);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object2);
            expect(factory.containsInSet(key, object1)).toBeTruthy();
            factory.eraseSet(key);
        });

        it('containsInSet', function() {
            var ob = {id: 4, string: 'string', number: 100};
            expect(factory.containsInSet(key, ob)).toBeFalsy();
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object1);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object2);
            expect(factory.containsInSet(key, object1)).toBeTruthy();
            expect(factory.containsInSet(key, ob)).toBeFalsy();
            factory.eraseSet(key);
        });

        it('getLength', function() {
            expect(factory.getLength(key)).toBe(0);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object1);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object2);
            expect(factory.getLength(key)).toBe(arr.length);
            factory.eraseSet(key);
        });

        it('getSet', function() {
            expect(factory.getSet(key)).toEqual([]);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object1);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object2);
            expect(factory.getSet(key)).toEqual(arr);
            factory.eraseSet(key);
        });

        it('eraseSet', function() {
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object1);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object2);
            expect(factory.getSet(key)).toEqual(arr);
            factory.eraseSet(key);
        });

        it('removeFromSet', function() {
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object1);
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object2);
            expect(factory.getSet(key)).toEqual(arr);
            factory.removeFromSet(key, object1);
            arr.splice(getIndex(arr, object1), 1);
            expect(factory.getSet(key)).toEqual(arr);
            factory.eraseSet(key);
        });

        it('updateInSet', function() {
            factory.saveInSet(key, object2);
            factory.saveInSet(key, object1);
            expect(factory.getSet(key)[getIndex(factory.getSet(key), object1)].number).toEqual(100);
            object1.number = 300;
            factory.updateInSet(key, object1);
            arr.splice(getIndex(arr, object1), 1);
            expect(factory.getSet(key)[getIndex(factory.getSet(key), object1)].number).toEqual(300);
        });
    });

});