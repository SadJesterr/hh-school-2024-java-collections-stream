package tasks;

import common.Area;
import common.Person;

import java.util.*;
import java.util.stream.Collectors;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {

    return persons.stream()
        .flatMap(person -> {
          Set<Integer> areaIds = personAreaIds.get(person.id());
          return areaIds.stream()
              .map(areaId -> String.format("%s - %s", person.firstName(),
                  areas.stream()
                      .collect(Collectors.toMap(Area::getId, Area::getName)).get(areaId)));
        })
        .collect(Collectors.toSet());
  }
}
