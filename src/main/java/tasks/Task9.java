package tasks;

import common.Person;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

//  private long count;
//  P.S убрана переменная count, больше негде использовать

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  // P.S. вместо удаления персоны из списка возможно стоит использовать subList
  // P.S. добавлен отлов person = null
  public List<String> getNames(List<Person> persons) {
    if (persons == null || persons.size() == 0) {
      return Collections.emptyList();
    }
    return persons.subList(1, persons.size())
        .stream()
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
//    return getNames(persons).stream().distinct().collect(Collectors.toSet());
//    P.S. .distinct не нужен, потому что toSet всё равно сделает множество
//    P.S.S кажется конструкция слишком громоздкая, возможно переписать
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  // P.S. переписано на StringBuilder, потому что при многократном использовании мы можем выиграть в скорости работы
  public String convertPersonToString(Person person) {
    String result = "";
    if (person.secondName() != null ||
        person.firstName() != null ||
        person.middleName() != null) {
      result =  String.format("%s %s %s",
          person.secondName(),
          person.firstName(),
          person.middleName());
    }
    return result;
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(1);
    for (Person person : persons) {
      if (!map.containsKey(person.id())) {
        map.put(person.id(), convertPersonToString(person));
      }
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
//  P.S. добавлен возврат значения has если нашли совпадения
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    for (Person person1 : persons1) {
      if (persons2.contains(persons1)) {
        return true;
      }
    }
    return false;
  }

  // Посчитать число четных чисел
  // P.S. Убрана переменная count, по сути можно не вводить лишнюю переменную и сразу возвращать количество
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  // если вызвать .equals у snapshot и set, то будет False (у них разный тип)
  // у set и snapshot совпадают ссылки на числа (например на число 1 ссылка @811 у обоих)
  // и из-за этого происходит совпадение последовательности чисел при вызове toString
  static void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
