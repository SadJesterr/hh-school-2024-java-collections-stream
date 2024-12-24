package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  // Если я правильно понял, то сделав словарь, можно ускорить работу то O(n)
  // т.е. для каждого id человека мы получаем из словаря за O(1) и сохраняем тот же порядок
  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Map<Integer, Person> persons = personService.findPersons(personIds)
        .stream().collect(Collectors.toMap(Person::id, person -> person));

    return personIds.stream()
        .map(persons::get)
        .collect(Collectors.toList());
  }
}
