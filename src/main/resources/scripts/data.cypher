// Topics section
create (rootTopic:Topic:_Topic {title:'root'})
create (programmingLangsTopic:Topic:_Topic {title:'Programming Languages'})
create (persistanceTopic:Topic:_Topic {title:'Persistence'})
create (queryLangsTopic:Topic:_Topic {title:'Query Languages'})
create (sqlTopic:Topic:_Topic {title:'SQL'})
create (hqlTopic:Topic:_Topic {title:'HQL'})
create (jdbcTopic:Topic:_Topic {title:'JDBC'})
create (javaTopic:Topic:_Topic {title:'Java'})
create (langsBasicsTopic:Topic:_Topic {title:'Language basics'})
create (javaeeTopic:Topic:_Topic {title:'Java EE'})
create (rmiTopic:Topic:_Topic {title:'Remote Method Invocation'})
create (servletsTopic:Topic:_Topic {title:'Servlets'})
create (jspTopic:Topic:_Topic {title:'JSP'})

create (objectsTopic:Topic:_Topic {title:'Object, class packaging'})
create (keywordsTopic:Topic:_Topic {title:'Keywords'})
create (exceptionsTopic:Topic:_Topic {title:'Exceptions'})
create (serializationTopic:Topic:_Topic {title:'Serialization'})
create (collectionsTopic:Topic:_Topic {title:'Collections'})
create (genericsTopic:Topic:_Topic {title:'Generics'})
create (annotationsTopic:Topic:_Topic {title:'Annotations'})
create (threadsTopic:Topic:_Topic {title:'Threads'})
create (garbageCollectorsTopic:Topic:_Topic {title:'Garbage Collectors'})
create (appletsTopic:Topic:_Topic {title:'Applets'})
create (swingTopic:Topic:_Topic {title:'Swing'})

// Topic tree section
create (rootTopic)-[:contains]->(programmingLangsTopic),
    (programmingLangsTopic)-[:contains]->(javaTopic),
        (javaTopic)-[:contains]->(langsBasicsTopic),
            (langsBasicsTopic)-[:contains]->(annotationsTopic),
            (langsBasicsTopic)-[:contains]->(collectionsTopic),
            (langsBasicsTopic)-[:contains]->(exceptionsTopic),
            (langsBasicsTopic)-[:contains]->(garbageCollectorsTopic),
            (langsBasicsTopic)-[:contains]->(genericsTopic),
            (langsBasicsTopic)-[:contains]->(keywordsTopic),
            (langsBasicsTopic)-[:contains]->(objectsTopic),
            (langsBasicsTopic)-[:contains]->(serializationTopic),
            (langsBasicsTopic)-[:contains]->(threadsTopic),
        (javaTopic)-[:contains]->(appletsTopic),
        (javaTopic)-[:contains]->(swingTopic),
        (javaTopic)-[:contains]->(javaeeTopic),
            (javaeeTopic)-[:contains]->(rmiTopic),
            (javaeeTopic)-[:contains]->(servletsTopic),
            (javaeeTopic)-[:contains]->(jspTopic),
(rootTopic)-[:contains]->(persistanceTopic),
    (persistanceTopic)-[:contains]->(queryLangsTopic),
        (queryLangsTopic)-[:contains]->(sqlTopic),
        (queryLangsTopic)-[:contains]->(hqlTopic),
    (persistanceTopic)-[:contains]->(jdbcTopic)

// Tags section
create (objectTag:Tag:_Tag {tag:'object'})
create (variableTag:Tag:_Tag {tag:'variable'})
create (classTag:Tag:_Tag {tag:'class'})
create (singletonTag:Tag:_Tag {tag:'singleton'})
create (listTag:Tag:_Tag {tag:'list'})
create (defaultValueTag:Tag:_Tag {tag:'default value'})
create (floatTag:Tag:_Tag {tag:'float'})
create (doubleTag:Tag:_Tag {tag:'double'})
create (staticTag:Tag:_Tag {tag:'static'})
create (javaTag:Tag:_Tag {tag:'java'})
create (codeTag:Tag:_Tag {tag:'code'})
create (fileTag:Tag:_Tag {tag:'file'})
create (jvmTag:Tag:_Tag {tag:'jvm'})
create (methodTag:Tag:_Tag {tag:'method'})
create (dataTag:Tag:_Tag {tag:'data'})
create (loadTag:Tag:_Tag {tag:'load'})
create (interfaceTag:Tag:_Tag {tag:'interface'})
create (abstractTag:Tag:_Tag {tag:'abstract'})
create (threadTag:Tag:_Tag {tag:'thread'})
create (applicationTag:Tag:_Tag {tag:'application'})
create (collectionTag:Tag:_Tag {tag:'collection'})
create (exceptionTag:Tag:_Tag {tag:'exception'})
create (rmiTag:Tag:_Tag {tag:'rmi'})
create (httpTag:Tag:_Tag {tag:'http'})
create (garbageTag:Tag:_Tag {tag:'garbage'})
create (servletTag:Tag:_Tag {tag:'servlet'})
create (serverTag:Tag:_Tag {tag:'server'})
create (webTag:Tag:_Tag {tag:'web'})
create (remoteTag:Tag:_Tag {tag:'remote'})
create (browserTag:Tag:_Tag {tag:'browser'})
create (finallyTag:Tag:_Tag {tag:'finally'})
create (requestTag:Tag:_Tag {tag:'request'})
create (jspTag:Tag:_Tag {tag:'jsp'})
create (appletTag:Tag:_Tag {tag:'applet'})

// Customers section
create (googleCustomer:Customer:_Customer {name:'Google'})
create (yandexCustomer:Customer:_Customer {name:'Yandex'})
create (cocaColaCustomer:Customer:_Customer {name:'CocaCola'})
create (ibmCustomer:Customer:_Customer {name:'IBM'})
create (adidasCustomer:Customer:_Customer {name:'Adidas'})
create (nikeCustomer:Customer:_Customer {name:'Nike'})
create (tutbyCustomer:Customer:_Customer {name:'TUT|by'})
create (sunCustomer:Customer:_Customer {name:'SUN'})
create (sapCustomer:Customer:_Customer {name:'SAP'})
create (oracleCustomer:Customer:_Customer {name:'Oracle'})
create (microsoftCustomer:Customer:_Customer {name:'Microsoft'})
create (appleCustomer:Customer:_Customer {name:'Apple'})
create (hpCustomer:Customer:_Customer {name:'Hewlett-Packard'})
create (foxConnCustomer:Customer:_Customer {name:'FoxConn'})
create (msiCustomer:Customer:_Customer {name:'MSI'})
create (gigabyteCustomer:Customer:_Customer {name:'Gigabyte'})
create (intelCustomer:Customer:_Customer {name:'Intel'})
create (amdCustomer:Customer:_Customer {name:'AMD'})
create (atiCustomer:Customer:_Customer {name:'ATI'})
create (nVidiaCustomer:Customer:_Customer {name:'nVidia'})
create (jonsonNJonsonCustomer:Customer:_Customer {name:'Jonson&Jonson'})
create (ñáåðáàíêCustomer:Customer:_Customer {name:'Ñáåðáàíê'})

// Questions section
create (q1:Question:_Question {question:'What is JVM? Why is Java called the “Platform Independent Programming Language”?',
        answer:'A Java virtual machine (JVM) is a process virtual machine that can execute Java bytecode. Each Java source file is compiled into a bytecode file, which is executed by the JVM. Java was designed to allow application programs to be built that could be run on any platform, without having to be rewritten or recompiled by the programmer for each separate platform. A Java virtual machine makes this possible, because it is aware of the specific instruction lengths and other particularities of the underlying hardware platform.'}),
    (javaTopic)-[:contains]->(q1),
    (q1)-[:has]->(ñáåðáàíêCustomer),
    (q1)-[:has]->(javaTag),
    (q1)-[:has]->(codeTag),
    (q1)-[:has]->(fileTag),
    (q1)-[:has]->(jvmTag)

create (q2:Question:_Question {question:'What is the Difference between JDK and JRE?',
        answer:'The Java Runtime Environment (JRE) is basically the Java Virtual Machine (JVM) where your Java programs are being executed. It also includes browser plugins for applet execution. The Java Development Kit (JDK) is the full featured Software Development Kit for Java, including the JRE, the compilers and tools (like JavaDoc, and Java Debugger), in order for a user to develop, compile and execute Java applications.'}),
    (javaTopic)-[:contains]->(q2),
    (q2)-[:has]->(javaTag)

create (q3:Question:_Question {question:'What does the “static” keyword mean? Can you override private or static method in Java?',
        answer:'The static keyword denotes that a member variable or method can be accessed, without requiring an instantiation of the class to which it belongs. A user cannot override static methods in Java, because method overriding is based upon dynamic binding at runtime and static methods are statically binded at compile time. A static method is not associated with any instance of a class so the concept is not applicable.'}),
    (javaTopic)-[:contains]->(q3),
    (q3)-[:has]->(javaTag),
    (q3)-[:has]->(methodTag),
    (q3)-[:has]->(classTag)

create (q4:Question:_Question {question:'Can you access non static variable in static context?',
        answer:'A static variable in Java belongs to its class and its value remains the same for all its instances. A static variable is initialized when the class is loaded by the JVM. If your code tries to access a non-static variable, without any instance, the compiler will complain, because those variables are not created yet and they are not associated with any instance.'}),
    (javaTopic)-[:contains]->(q4),
    (q4)-[:has]->(classTag)

create (q5:Question:_Question {question:'What are the Data Types supported by Java? What is Autoboxing and Unboxing?',
        answer:'The eight primitive data types supported by the Java programming language are:

    byte
    short
    int
    long
    float
    double
    boolean
    char

Autoboxing is the automatic conversion made by the Java compiler between the primitive types and their corresponding object wrapper classes. For example, the compiler converts an int to an Integer, a double to a Double, and so on. If the conversion goes the other way, this operation is called unboxing.'}),
    (javaTopic)-[:contains]->(q5),
    (q5)-[:has]->(javaTag),
    (q5)-[:has]->(dataTag)

create (q6:Question:_Question {question:'What is Function Overriding and Overloading in Java?',
        answer:'Method overloading in Java occurs when two or more methods in the same class have the exact same name, but different parameters. On the other hand, method overriding is defined as the case when a child class redefines the same method as a parent class. Overridden methods must have the same name, argument list, and return type. The overriding method may not limit the access of the method it overrides.'}),
    (javaTopic)-[:contains]->(q6),
    (q6)-[:has]->(appleCustomer),
    (q6)-[:has]->(javaTag),
    (q6)-[:has]->(methodTag),
    (q6)-[:has]->(classTag),
    (q6)-[:has]->(loadTag)

create (q7:Question:_Question {question:'What is a Constructor, Constructor Overloading in Java and Copy-Constructor?',
        answer:'A constructor gets invoked when a new object is created. Every class has a constructor. In case the programmer does not provide a constructor for a class, the Java compiler (Javac) creates a default constructor for that class. The constructor overloading is similar to method overloading in Java. Different constructors can be created for a single class. Each constructor must have its own unique parameter list. Finally, Java does support copy constructors like C++, but the difference lies in the fact that Java doesn’t create a default copy constructor if you don’t write your own.'}),
    (javaTopic)-[:contains]->(q7),
    (q7)-[:has]->(msiCustomer),
    (q7)-[:has]->(javaTag),
    (q7)-[:has]->(classTag),
    (q7)-[:has]->(loadTag)

create (q8:Question:_Question {question:'Does Java support multiple inheritance?',
        answer:'No, Java does not support multiple inheritance. Each class is able to extend only on one class, but is able to implement more than one interfaces.'}),
    (javaTopic)-[:contains]->(q8),
    (q8)-[:has]->(ñáåðáàíêCustomer),
    (q8)-[:has]->(javaTag),
    (q8)-[:has]->(classTag)

create (q9:Question:_Question {question:'What is the difference between an Interface and an Abstract class?',
        answer:'Java provides and supports the creation both of abstract classes and interfaces. Both implementations share some common characteristics, but they differ in the following features:

    All methods in an interface are implicitly abstract. On the other hand, an abstract class may contain both abstract and non-abstract methods.
    A class may implement a number of Interfaces, but can extend only one abstract class.
    In order for a class to implement an interface, it must implement all its declared methods. However, a class may not implement all declared methods of an abstract class. Though, in this case, the sub-class must also be declared as abstract.
    Abstract classes can implement interfaces without even providing the implementation of interface methods.
    Variables declared in a Java interface is by default final. An abstract class may contain non-final variables.
    Members of a Java interface are public by default. A member of an abstract class can either be private, protected or public.
    An interface is absolutely abstract and cannot be instantiated. An abstract class also cannot be instantiated, but can be invoked if it contains a main method.'}),
    (javaTopic)-[:contains]->(q9),
    (q9)-[:has]->(javaTag),
    (q9)-[:has]->(methodTag),
    (q9)-[:has]->(classTag),
    (q9)-[:has]->(interfaceTag),
    (q9)-[:has]->(abstractTag)

create (q10:Question:_Question {question:'What are pass by reference and pass by value?',
        answer:'When an object is passed by value, this means that a copy of the object is passed. Thus, even if changes are made to that object, it doesn’t affect the original value. When an object is passed by reference, this means that the actual object is not passed, rather a reference of the object is passed. Thus, any changes made by the external method, are also reflected in all places.'}),
    (javaTopic)-[:contains]->(q10),
    (q10)-[:has]->(sunCustomer),
    (q10)-[:has]->(objectTag)

create (q12:Question:_Question {question:'What is the difference between processes and threads?',
        answer:'A process is an execution of a program, while a Thread is a single execution sequence within a process. A process can contain multiple threads. A Thread is sometimes called a lightweight process.'}),
    (threadsTopic)-[:contains]->(q12),
    (q12)-[:has]->(foxConnCustomer),
    (q12)-[:has]->(threadTag)

create (q13:Question:_Question {question:'Explain different ways of creating a thread. Which one would you prefer and why?',
        answer:'There are three ways that can be used in order for a Thread to be created:

    A class may extend the Thread class.
    A class may implement the Runnable interface.
    An application can use the Executor framework, in order to create a thread pool.

The Runnable interface is preferred, as it does not require an object to inherit the Thread class. In case your application design requires multiple inheritance, only interfaces can help you. Also, the thread pool is very efficient and can be implemented and used very easily.'}),
    (threadsTopic)-[:contains]->(q13),
    (q13)-[:has]->(appleCustomer),
    (q13)-[:has]->(classTag),
    (q13)-[:has]->(interfaceTag),
    (q13)-[:has]->(threadTag),
    (q13)-[:has]->(applicationTag)

create (q14:Question:_Question {question:'Explain the available thread states in a high-level.',
        answer:'During its execution, a thread can reside in one of the following states:

    Runnable: A thread becomes ready to run, but does not necessarily start running immediately.
    Running: The processor is actively executing the thread code.
    Waiting: A thread is in a blocked state waiting for some external processing to finish.
    Sleeping: The thread is forced to sleep.
    Blocked on I/O: Waiting for an I/O operation to complete.
    Blocked on Synchronization: Waiting to acquire a lock.
    Dead: The thread has finished its execution.
'}),
    (threadsTopic)-[:contains]->(q14),
    (q14)-[:has]->(hpCustomer),
    (q14)-[:has]->(threadTag)

create (q15:Question:_Question {question:'What is the difference between a synchronized method and a synchronized block?',
        answer:'In Java programming, each object has a lock. A thread can acquire the lock for an object by using the synchronized keyword. The synchronized keyword can be applied in a method level (coarse grained lock) or block level of code (fine grained lock).'}),
    (threadsTopic)-[:contains]->(q15),
    (q15)-[:has]->(methodTag),
    (q15)-[:has]->(objectTag)

create (q16:Question:_Question {question:'How does thread synchronization occurs inside a monitor? What levels of synchronization can you apply?',
        answer:'The JVM uses locks in conjunction with monitors. A monitor is basically a guardian that watches over a sequence of synchronized code and ensuring that only one thread at a time executes a synchronized piece of code. Each monitor is associated with an object reference. The thread is not allowed to execute the code until it obtains the lock.'}),
    (threadsTopic)-[:contains]->(q16),
    (q16)-[:has]->(threadTag),
    (q16)-[:has]->(codeTag)

create (q17:Question:_Question {question:'What’s a deadlock?',
        answer:'A condition that occurs when two processes are waiting for each other to complete, before proceeding. The result is that both processes wait endlessly.'}),
    (threadsTopic)-[:contains]->(q17),
    (q17)-[:has]->(intelCustomer)

create (q18:Question:_Question {question:'How do you ensure that N threads can access N resources without deadlock?',
        answer:'A very simple way to avoid deadlock while using N threads is to impose an ordering on the locks and force each thread to follow that ordering. Thus, if all threads lock and unlock the mutexes in the same order, no deadlocks can arise.'}),
    (threadsTopic)-[:contains]->(q18),
    (q18)-[:has]->(nikeCustomer),
    (q18)-[:has]->(threadTag)

create (q20:Question:_Question {question:'What are the basic interfaces of Java Collections Framework?',
        answer:'Java Collections Framework provides a well designed set of interfaces and classes that support operations on a collections of objects. The most basic interfaces that reside in the Java Collections Framework are:

    Collection, which represents a group of objects known as its elements.
    Set, which is a collection that cannot contain duplicate elements.
    List, which is an ordered collection and can contain duplicate elements.
    Map, which is an object that maps keys to values and cannot contain duplicate keys.
'}),
    (collectionsTopic)-[:contains]->(q20),
    (q20)-[:has]->(javaTag),
    (q20)-[:has]->(objectTag),
    (q20)-[:has]->(collectionTag),
    (q20)-[:has]->(interfaceTag)

create (q21:Question:_Question {question:'Why Collection doesn’t extend Cloneable and Serializable interfaces?',
        answer:'The Collection interface specifies groups of objects known as elements. Each concrete implementation of a Collection can choose its own way of how to maintain and order its elements. Some collections allow duplicate keys, while some other collections don’t. The semantics and the implications of either cloning or serialization come into play when dealing with actual implementations. Thus, the concrete implementations of collections should decide how they can be cloned or serialized.'}),
    (collectionsTopic)-[:contains]->(q21),
    (q21)-[:has]->(hpCustomer),
    (q21)-[:has]->(collectionTag),
    (q21)-[:has]->(interfaceTag)

create (q22:Question:_Question {question:'What is an Iterator?',
        answer:'The Iterator interface provides a number of methods that are able to iterate over any Collection. Each Java Collection contains the iterator method that returns an Iterator instance. Iterators are capable of removing elements from the underlying collection during the iteration.'}),
    (collectionsTopic)-[:contains]->(q22),
    (q22)-[:has]->(ñáåðáàíêCustomer),
    (q22)-[:has]->(methodTag),
    (q22)-[:has]->(collectionTag)

create (q23:Question:_Question {question:'What differences exist between Iterator and ListIterator?',
        answer:'The differences of these elements are listed below:

    An Iterator can be used to traverse the Set and List collections, while the ListIterator can be used to iterate only over Lists.
    The Iterator can traverse a collection only in forward direction, while the ListIterator can traverse a List in both directions.
    The ListIterator implements the Iterator interface and contains extra functionality, such as adding an element, replacing an element, getting the index position for previous and next elements, etc.
'}),
    (collectionsTopic)-[:contains]->(q23),
    (q23)-[:has]->(sunCustomer),
    (q23)-[:has]->(collectionTag)

create (q24:Question:_Question {question:'What is difference between fail-fast and fail-safe?',
        answer:'The Iterator`s fail-safe property works with the clone of the underlying collection and thus, it is not affected by any modification in the collection. All the collection classes in java.util package are fail-fast, while the collection classes in java.util.concurrent are fail-safe. Fail-fast iterators throw a ConcurrentModificationException, while fail-safe iterator never throws such an exception.'}),
    (collectionsTopic)-[:contains]->(q24),
    (q24)-[:has]->(appleCustomer),
    (q24)-[:has]->(javaTag),
    (q24)-[:has]->(classTag),
    (q24)-[:has]->(collectionTag),
    (q24)-[:has]->(exceptionTag)

create (q25:Question:_Question {question:'How HashMap works in Java?',
        answer:'A HashMap in Java stores key-value pairs. The HashMap requires a hash function and uses hashCode and equals methods, in order to put and retrieve elements to and from the collection respectively. When the put method is invoked, the HashMap calculates the hash value of the key and stores the pair in the appropriate index inside the collection. If the key exists, its value is updated with the new value. Some important characteristics of a HashMap are its capacity, its load factor and the threshold resizing.'}),
    (collectionsTopic)-[:contains]->(q25),
    (q25)-[:has]->(hpCustomer),
    (q25)-[:has]->(javaTag),
    (q25)-[:has]->(methodTag),
    (q25)-[:has]->(collectionTag)

create (q26:Question:_Question {question:'What is the importance of hashCode() and equals() methods?',
        answer:'A HashMap in Java uses the hashCode and equals methods to determine the index of the key-value pair. These methods are also used when we request the value of a specific key. If these methods are not implemented correctly, two different keys might produce the same hash value and thus, will be considered as equal by the collection. Furthermore, these methods are also used to detect duplicates. Thus, the implementation of both methods is crucial to the accuracy and correctness of the HashMap.'}),
    (collectionsTopic)-[:contains]->(q26),
    (q26)-[:has]->(methodTag),
    (q26)-[:has]->(codeTag)

create (q27:Question:_Question {question:'What differences exist between HashMap and Hashtable?',
        answer:'Both the HashMap and Hashtable classes implement the Map interface and thus, have very similar characteristics. However, they differ in the following features:

    A HashMap allows the existence of null keys and values, while a Hashtable doesn’t allow neither null keys, nor null values.
    A Hashtable is synchronized, while a HashMap is not. Thus, HashMap is preferred in single-threaded environments, while a Hashtable is suitable for multi-threaded environments.
    A HashMap provides its set of keys and a Java application can iterate over them. Thus, a HashMap is fail-fast. On the other hand, a Hashtable provides an Enumeration of its keys.
    The Hashtable class is considered to be a legacy class.
'}),
    (collectionsTopic)-[:contains]->(q27),
    (q27)-[:has]->(intelCustomer),
    (q27)-[:has]->(classTag),
    (q27)-[:has]->(threadTag)

create (q28:Question:_Question {question:'What is difference between Array and ArrayList? When will you use Array over ArrayList?',
        answer:'The Array and ArrayList classes differ on the following features:

    Arrays can contain primitive or objects, while an ArrayList can contain only objects.
    Arrays have fixed size, while an ArrayList is dynamic.
    An ArrayListprovides more methods and features, such as addAll, removeAll, iterator, etc.
    For a list of primitive data types, the collections use autoboxing to reduce the coding effort. However, this approach makes them slower when working on fixed size primitive data types.
'}),
    (collectionsTopic)-[:contains]->(q28),
    (q28)-[:has]->(objectTag),
    (q28)-[:has]->(dataTag)

create (q29:Question:_Question {question:'What is difference between ArrayList and LinkedList?',
        answer:'Both the ArrayList and LinkedList classes implement the List interface, but they differ on the following features:

    An ArrayList is an index based data structure backed by an Array. It provides random access to its elements with a performance equal to O(1). On the other hand, a LinkedList stores its data as list of elements and every element is linked to its previous and next element. In this case, the search operation for an element has execution time equal to O(n).
    The Insertion, addition and removal operations of an element are faster in a LinkedList compared to an ArrayList, because there is no need of resizing an array or updating the index when an element is added in some arbitrary position inside the collection.
    A LinkedList consumes more memory than an ArrayList, because every node in a LinkedList stores two references, one for its previous element and one for its next element.
'}),
    (collectionsTopic)-[:contains]->(q29),
    (q29)-[:has]->(dataTag)

create (q30:Question:_Question {question:'What is Comparable and Comparator interface?',
        answer:'List their differences. Java provides the Comparable interface, which contains only one method, called compareTo. This method compares two objects, in order to impose an order between them. Specifically, it returns a negative integer, zero, or a positive integer to indicate that the input object is less than, equal or greater than the existing object. Java provides the Comparator interface, which contains two methods, called compare and equals. The first method compares its two input arguments and imposes an order between them. It returns a negative integer, zero, or a positive integer to indicate that the first argument is less than, equal to, or greater than the second. The second method requires an object as a parameter and aims to decide whether the input object is equal to the comparator. The method returns true, only if the specified object is also a comparator and it imposes the same ordering as the comparator.'}),
    (collectionsTopic)-[:contains]->(q30),
    (q30)-[:has]->(yandexCustomer),
    (q30)-[:has]->(javaTag),
    (q30)-[:has]->(methodTag),
    (q30)-[:has]->(objectTag),
    (q30)-[:has]->(interfaceTag)

create (q31:Question:_Question {question:'What is Java Priority Queue?',
        answer:'The PriorityQueue is an unbounded queue, based on a priority heap and its elements are ordered in their natural order. At the time of its creation, we can provide a Comparator that is responsible for ordering the elements of the PriorityQueue. A PriorityQueue doesn’t allow null values, those objects that doesn’t provide natural ordering, or those objects that don’t have any comparator associated with them. Finally, the Java PriorityQueue is not thread-safe and it requires O(log(n)) time for its enqueing and dequeing operations.'}),
    (collectionsTopic)-[:contains]->(q31),
    (q31)-[:has]->(javaTag),
    (q31)-[:has]->(objectTag)

create (q32:Question:_Question {question:'What do you know about the big-O notation and can you give some examples with respect to different data structures?',
        answer:'The Big-O notation simply describes how well an algorithm scales or performs in the worst case scenario as the number of elements in a data structure increases. The Big-O notation can also be used to describe other behavior such as memory consumption. Since the collection classes are actually data structures, we usually use the Big-O notation to chose the best implementation to use, based on time, memory and performance. Big-O notation can give a good indication about performance for large amounts of data.'}),
    (collectionsTopic)-[:contains]->(q32),
    (q32)-[:has]->(dataTag)

create (q33:Question:_Question {question:'What is the tradeoff between using an unordered array versus an ordered array?',
        answer:'The major advantage of an ordered array is that the search times have time complexity of O(log n), compared to that of an unordered array, which is O (n). The disadvantage of an ordered array is that the insertion operation has a time complexity of O(n), because the elements with higher values must be moved to make room for the new element. Instead, the insertion operation for an unordered array takes constant time of O(1).'}),
    (collectionsTopic)-[:contains]->(q33),
    (q33)-[:has]->(ñáåðáàíêCustomer)

create (q34:Question:_Question {question:'What are some of the best practices relating to the Java Collection framework?',
        answer:'

    Choosing the right type of the collection to use, based on the application’s needs, is very crucial for its performance. For example if the size of the elements is fixed and know a priori, we shall use an Array, instead of an ArrayList.
    Some collection classes allow us to specify their initial capacity. Thus, if we have an estimation on the number of elements that will be stored, we can use it to avoid rehashing or resizing.
    Always use Generics for type-safety, readability, and robustness. Also, by using Generics you avoid the ClassCastException during runtime.
    Use immutable classes provided by the Java Development Kit (JDK) as a key in a Map, in order to avoid the implementation of the hashCode and equals methods for our custom class.
    Program in terms of interface not implementation.
    Return zero-length collections or arrays as opposed to returning a null in case the underlying collection is actually empty.
'}),
    (collectionsTopic)-[:contains]->(q34),
    (q34)-[:has]->(intelCustomer),
    (q34)-[:has]->(javaTag),
    (q34)-[:has]->(classTag),
    (q34)-[:has]->(collectionTag)

create (q35:Question:_Question {question:'What’s the difference between Enumeration and Iterator interfaces?',
        answer:'Enumeration is twice as fast as compared to an Iterator and uses very less memory. However, the Iterator is much safer compared to Enumeration, because other threads are not able to modify the collection object that is currently traversed by the iterator. Also, Iteratorsallow the caller to remove elements from the underlying collection, something which is not possible with Enumerations.'}),
    (collectionsTopic)-[:contains]->(q35),
    (q35)-[:has]->(oracleCustomer),
    (q35)-[:has]->(collectionTag),
    (q35)-[:has]->(interfaceTag)

create (q36:Question:_Question {question:'What is the difference between HashSet and TreeSet?',
        answer:'The HashSet is Implemented using a hash table and thus, its elements are not ordered. The add, remove, and contains methods of a HashSet have constant time complexity O(1). On the other hand, a TreeSet is implemented using a tree structure. The elements in a TreeSet are sorted, and thus, the add, remove, and contains methods have time complexity of O(logn).'}),
    (collectionsTopic)-[:contains]->(q36),
    (q36)-[:has]->(appleCustomer),
    (q36)-[:has]->(methodTag)

create (q38:Question:_Question {question:'What is the purpose of garbage collection in Java, and when is it used?',
        answer:'The purpose of garbage collection is to identify and discard those objects that are no longer needed by the application, in order for the resources to be reclaimed and reused.'}),
    (garbageCollectorsTopic)-[:contains]->(q38),
    (q38)-[:has]->(yandexCustomer),
    (q38)-[:has]->(javaTag),
    (q38)-[:has]->(collectionTag),
    (q38)-[:has]->(garbageTag)

create (q39:Question:_Question {question:'What does System.gc() and Runtime.gc() methods do?',
        answer:'These methods can be used as a hint to the JVM, in order to start a garbage collection. However, this it is up to the Java Virtual Machine (JVM) to start the garbage collection immediately or later in time.'}),
    (garbageCollectorsTopic)-[:contains]->(q39),
    (q39)-[:has]->(hpCustomer),
    (q39)-[:has]->(methodTag),
    (q39)-[:has]->(collectionTag),
    (q39)-[:has]->(garbageTag),
    (q39)-[:has]->(jvmTag)

create (q40:Question:_Question {question:'When is the finalize() called? What is the purpose of finalization?',
        answer:'The finalize method is called by the garbage collector, just before releasing the object’s memory. It is normally advised to release resources held by the object inside the finalize method.'}),
    (garbageCollectorsTopic)-[:contains]->(q40),
    (q40)-[:has]->(methodTag),
    (q40)-[:has]->(objectTag)

create (q41:Question:_Question {question:'If an object reference is set to null, will the Garbage Collector immediately free the memory held by that object?',
        answer:'No, the object will be available for garbage collection in the next cycle of the garbage collector.'}),
    (garbageCollectorsTopic)-[:contains]->(q41),
    (q41)-[:has]->(oracleCustomer),
    (q41)-[:has]->(objectTag),
    (q41)-[:has]->(garbageTag)

create (q42:Question:_Question {question:'What is structure of Java Heap? What is Perm Gen space in Heap?',
        answer:'The JVM has a heap that is the runtime data area from which memory for all class instances and arrays is allocated. It is created at the JVM start-up. Heap memory for objects is reclaimed by an automatic memory management system which is known as a garbage collector. Heap memory consists of live and dead objects. Live objects are accessible by the application and will not be a subject of garbage collection. Dead objects are those which will never be accessible by the application, but have not been collected by the garbage collector yet. Such objects occupy the heap memory space until they are eventually collected by the garbage collector.'}),
    (garbageCollectorsTopic)-[:contains]->(q42),
    (q42)-[:has]->(sapCustomer),
    (q42)-[:has]->(javaTag),
    (q42)-[:has]->(objectTag),
    (q42)-[:has]->(garbageTag),
    (q42)-[:has]->(applicationTag),
    (q42)-[:has]->(jvmTag)

create (q43:Question:_Question {question:'What is the difference between Serial and Throughput Garbage collector?',
        answer:'The throughput garbage collector uses a parallel version of the young generation collector and is meant to be used with applications that have medium to large data sets. On the other hand, the serial collector is usually adequate for most small applications (those requiring heaps of up to approximately 100MB on modern processors).'}),
    (garbageCollectorsTopic)-[:contains]->(q43),
    (q43)-[:has]->(amdCustomer),
    (q43)-[:has]->(garbageTag),
    (q43)-[:has]->(applicationTag)

create (q44:Question:_Question {question:'When does an Object becomes eligible for Garbage collection in Java?',
        answer:'A Java object is subject to garbage collection when it becomes unreachable to the program in which it is currently used.'}),
    (garbageCollectorsTopic)-[:contains]->(q44),
    (q44)-[:has]->(intelCustomer),
    (q44)-[:has]->(javaTag),
    (q44)-[:has]->(objectTag),
    (q44)-[:has]->(collectionTag),
    (q44)-[:has]->(garbageTag)

create (q45:Question:_Question {question:'Does Garbage collection occur in permanent generation space in JVM?',
        answer:'Garbage Collection does occur in PermGen space and if PermGen space is full or cross a threshold, it can trigger a full garbage collection. If you look carefully at the output of the garbage collector, you will find that PermGen space is also garbage collected. This is the reason why correct sizing of PermGen space is important to avoid frequent full garbage collections.'}),
    (garbageCollectorsTopic)-[:contains]->(q45),
    (q45)-[:has]->(atiCustomer),
    (q45)-[:has]->(collectionTag),
    (q45)-[:has]->(garbageTag),
    (q45)-[:has]->(jvmTag)

create (q47:Question:_Question {question:'What are the two types of Exceptions in Java? Which are the differences between them?',
        answer:'Java has two types of exceptions: checked exceptions and unchecked exceptions. Unchecked exceptions do not need to be declared in a method or a constructor’s throws clause, if they can be thrown by the execution of the method or the constructor, and propagate outside the method or constructor boundary. On the other hand, checked exceptions must be declared in a method or a constructor’s throws clause. See here for tips on Java exception handling.'}),
    (exceptionsTopic)-[:contains]->(q47),
    (q47)-[:has]->(javaTag),
    (q47)-[:has]->(methodTag),
    (q47)-[:has]->(exceptionTag)

create (q48:Question:_Question {question:'What is the difference between Exception and Error in java?',
        answer:'Exception and Error classes are both subclasses of the Throwable class. The Exception class is used for exceptional conditions that a user’s program should catch. The Error class defines exceptions that are not excepted to be caught by the user program.'}),
    (exceptionsTopic)-[:contains]->(q48),
    (q48)-[:has]->(javaTag),
    (q48)-[:has]->(classTag),
    (q48)-[:has]->(exceptionTag)

create (q49:Question:_Question {question:'What is the difference between throw and throws?',
        answer:'The throw keyword is used to explicitly raise a exception within the program. On the contrary, the throws clause is used to indicate those exceptions that are not handled by a method. Each method must explicitly specify which exceptions does not handle, so the callers of that method can guard against possible exceptions. Finally, multiple exceptions are separated by a comma.'}),
    (exceptionsTopic)-[:contains]->(q49),
    (q49)-[:has]->(cocaColaCustomer),
    (q49)-[:has]->(methodTag),
    (q49)-[:has]->(exceptionTag)

create (q50:Question:_Question {question:'What is the importance of finally block in exception handling?',
        answer:'A finally block will always be executed, whether or not an exception is actually thrown. Even in the case where the catch statement is missing and an exception is thrown, the finally block will still be executed. Last thing to mention is that the finally block is used to release resources like I/O buffers, database connections, etc.'}),
    (exceptionsTopic)-[:contains]->(q50),
    (q50)-[:has]->(sunCustomer),
    (q50)-[:has]->(finallyTag),
    (q50)-[:has]->(exceptionTag)

create (q51:Question:_Question {question:'What will happen to the Exception object after exception handling?',
        answer:'The Exception object will be garbage collected in the next garbage collection.'}),
    (exceptionsTopic)-[:contains]->(q51),
    (q51)-[:has]->(objectTag),
    (q51)-[:has]->(garbageTag),
    (q51)-[:has]->(exceptionTag)

create (q52:Question:_Question {question:'How does finally block differ from finalize() method?',
        answer:'A finally block will be executed whether or not an exception is thrown and is used to release those resources held by the application. Finalize is a protected method of the Object class, which is called by the Java Virtual Machine (JVM) just before an object is garbage collected.'}),
    (exceptionsTopic)-[:contains]->(q52),
    (q52)-[:has]->(ñáåðáàíêCustomer),
    (q52)-[:has]->(methodTag),
    (q52)-[:has]->(objectTag),
    (q52)-[:has]->(finallyTag)

create (q54:Question:_Question {question:'What is an Applet?',
        answer:'A java applet is program that can be included in a HTML page and be executed in a java enabled client browser. Applets are used for creating dynamic and interactive web applications.'}),
    (appletsTopic)-[:contains]->(q54),
    (q54)-[:has]->(appleCustomer),
    (q54)-[:has]->(javaTag),
    (q54)-[:has]->(appletTag)

create (q55:Question:_Question {question:'Explain the life cycle of an Applet.',
        answer:'An applet may undergo the following states:

    Init: An applet is initialized each time is loaded.
    Start: Begin the execution of an applet.
    Stop: Stop the execution of an applet.
    Destroy: Perform a final cleanup, before unloading the applet.
'}),
    (appletsTopic)-[:contains]->(q55),
    (q55)-[:has]->(adidasCustomer),
    (q55)-[:has]->(appletTag),
    (q55)-[:has]->(loadTag)

create (q56:Question:_Question {question:'What happens when an applet is loaded?',
        answer:'First of all, an instance of the applet’s controlling class is created. Then, the applet initializes itself and finally, it starts running.'}),
    (appletsTopic)-[:contains]->(q56),
    (q56)-[:has]->(ibmCustomer),
    (q56)-[:has]->(appletTag),
    (q56)-[:has]->(loadTag)

create (q57:Question:_Question {question:'What is the difference between an Applet and a Java Application?',
        answer:'Applets are executed within a java enabled browser, but a Java application is a standalone Java program that can be executed outside of a browser. However, they both require the existence of a Java Virtual Machine (JVM). Furthermore, a Java application requires a main method with a specific signature, in order to start its execution. Java applets don’t need such a method to start their execution. Finally, Java applets typically use a restrictive security policy, while Java applications usually use more relaxed security policies.'}),
    (appletsTopic)-[:contains]->(q57),
    (q57)-[:has]->(hpCustomer),
    (q57)-[:has]->(javaTag),
    (q57)-[:has]->(methodTag),
    (q57)-[:has]->(appletTag),
    (q57)-[:has]->(applicationTag),
    (q57)-[:has]->(browserTag)

create (q58:Question:_Question {question:'What are the restrictions imposed on Java applets?',
        answer:'Mostly due to security reasons, the following restrictions are imposed on Java applets:

    An applet cannot load libraries or define native methods.
    An applet cannot ordinarily read or write files on the execution host.
    An applet cannot read certain system properties.
    An applet cannot make network connections except to the host that it came from.
    An applet cannot start any program on the host that’s executing it.
'}),
    (appletsTopic)-[:contains]->(q58),
    (q58)-[:has]->(amdCustomer),
    (q58)-[:has]->(javaTag),
    (q58)-[:has]->(appletTag)

create (q59:Question:_Question {question:'What are untrusted applets?',
        answer:'Untrusted applets are those Java applets that cannot access or execute local system files. By default, all downloaded applets are considered as untrusted.'}),
    (appletsTopic)-[:contains]->(q59),
    (q59)-[:has]->(googleCustomer),
    (q59)-[:has]->(appletTag)

create (q60:Question:_Question {question:'What is the difference between applets loaded over the internet and applets loaded via the file system?',
        answer:'Regarding the case where an applet is loaded over the internet, the applet is loaded by the applet classloader and is subject to the restrictions enforced by the applet security manager. Regarding the case where an applet is loaded from the client’s local disk, the applet is loaded by the file system loader. Applets loaded via the file system are allowed to read files, write files and to load libraries on the client. Also, applets loaded via the file system are allowed to execute processes and finally, applets loaded via the file system are not passed through the byte code verifier.'}),
    (appletsTopic)-[:contains]->(q60),
    (q60)-[:has]->(yandexCustomer),
    (q60)-[:has]->(appletTag),
    (q60)-[:has]->(fileTag),
    (q60)-[:has]->(loadTag)

create (q61:Question:_Question {question:'What is the applet class loader, and what does it provide?',
        answer:'When an applet is loaded over the internet, the applet is loaded by the applet classloader. The class loader enforces the Java name space hierarchy. Also, the class loader guarantees that a unique namespace exists for classes that come from the local file system, and that a unique namespace exists for each network source. When a browser loads an applet over the net, that applet’s classes are placed in a private namespace associated with the applet’s origin. Then, those classes loaded by the class loader are passed through the verifier.The verifier checks that the class file conforms to the Java language specification . Among other things, the verifier ensures that there are no stack overflows or underflows and that the parameters to all bytecode instructions are correct.'}),
    (appletsTopic)-[:contains]->(q61),
    (q61)-[:has]->(adidasCustomer),
    (q61)-[:has]->(javaTag),
    (q61)-[:has]->(classTag),
    (q61)-[:has]->(appletTag),
    (q61)-[:has]->(fileTag),
    (q61)-[:has]->(loadTag)

create (q62:Question:_Question {question:'What is the applet security manager, and what does it provide?',
        answer:'The applet security manager is a mechanism to impose restrictions on Java applets. A browser may only have one security manager. The security manager is established at startup, and it cannot thereafter be replaced, overloaded, overridden, or extended.'}),
    (appletsTopic)-[:contains]->(q62),
    (q62)-[:has]->(cocaColaCustomer),
    (q62)-[:has]->(appletTag)

create (q64:Question:_Question {question:'What is the difference between a Choice and a List?',
        answer:'A Choice is displayed in a compact form that must be pulled down, in order for a user to be able to see the list of all available choices. Only one item may be selected from a Choice. A List may be displayed in such a way that several List items are visible. A List supports the selection of one or more List items.'}),
    (swingTopic)-[:contains]->(q64),
    (q64)-[:has]->(amdCustomer)

create (q65:Question:_Question {question:'What is a layout manager?',
        answer:'A layout manager is the used to organize the components in a container.'}),
    (swingTopic)-[:contains]->(q65)

create (q66:Question:_Question {question:'What is the difference between a Scrollbar and a JScrollPane?',
        answer:'A Scrollbar is a Component, but not a Container. A ScrollPane is a Container. A ScrollPane handles its own events and performs its own scrolling.'}),
    (swingTopic)-[:contains]->(q66),
    (q66)-[:has]->(appleCustomer)

create (q67:Question:_Question {question:'Which Swing methods are thread-safe?',
        answer:'There are only three thread-safe methods: repaint, revalidate, and invalidate.'}),
    (swingTopic)-[:contains]->(q67),
    (q67)-[:has]->(methodTag),
    (q67)-[:has]->(threadTag)

create (q68:Question:_Question {question:'Name three Component subclasses that support painting.',
        answer:'The Canvas, Frame, Panel, and Applet classes support painting.'}),
    (swingTopic)-[:contains]->(q68),
    (q68)-[:has]->(ibmCustomer),
    (q68)-[:has]->(classTag)

create (q69:Question:_Question {question:'What is clipping?',
        answer:'Clipping is defined as the process of confining paint operations to a limited area or shape.'}),
    (swingTopic)-[:contains]->(q69)

create (q70:Question:_Question {question:'What is the difference between a MenuItem and a CheckboxMenuItem?',
        answer:'The CheckboxMenuItem class extends the MenuItem class and supports a menu item that may be either checked or unchecked.'}),
    (swingTopic)-[:contains]->(q70),
    (q70)-[:has]->(classTag)

create (q71:Question:_Question {question:'How are the elements of a BorderLayout organized?',
        answer:'The elements of a BorderLayout are organized at the borders (North, South, East, and West) and the center of a container.'}),
    (swingTopic)-[:contains]->(q71),
    (q71)-[:has]->(nikeCustomer)

create (q72:Question:_Question {question:'How are the elements of a GridBagLayout organized?',
        answer:'The elements of a GridBagLayout are organized according to a grid. The elements are of different sizes and may occupy more than one row or column of the grid. Thus, the rows and columns may have different sizes.'}),
    (swingTopic)-[:contains]->(q72)

create (q73:Question:_Question {question:'What is the difference between a Window and a Frame?',
        answer:'The Frame class extends the Window class and defines a main application window that can have a menu bar.'}),
    (swingTopic)-[:contains]->(q73),
    (q73)-[:has]->(tutbyCustomer),
    (q73)-[:has]->(classTag)

create (q74:Question:_Question {question:'What is the relationship between clipping and repainting?',
        answer:'When a window is repainted by the AWT painting thread, it sets the clipping regions to the area of the window that requires repainting.'}),
    (swingTopic)-[:contains]->(q74),
    (q74)-[:has]->(sunCustomer)

create (q75:Question:_Question {question:'What is the relationship between an event-listener interface and an event-adapter class?',
        answer:'An event-listener interface defines the methods that must be implemented by an event handler for a particular event. An event adapter provides a default implementation of an event-listener interface.'}),
    (swingTopic)-[:contains]->(q75),
    (q75)-[:has]->(sapCustomer),
    (q75)-[:has]->(classTag),
    (q75)-[:has]->(interfaceTag)

create (q76:Question:_Question {question:'How can a GUI component handle its own events?',
        answer:'A GUI component can handle its own events, by implementing the corresponding event-listener interface and adding itself as its own event listener.'}),
    (swingTopic)-[:contains]->(q76)

create (q77:Question:_Question {question:'What advantage do Java’s layout managers provide over traditional windowing systems?',
        answer:'Java uses layout managers to lay out components in a consistent manner, across all windowing platforms. Since layout managers aren’t tied to absolute sizing and positioning, they are able to accomodate platform-specific differences among windowing systems.'}),
    (swingTopic)-[:contains]->(q77),
    (q77)-[:has]->(javaTag)

create (q78:Question:_Question {question:'What is the design pattern that Java uses for all Swing components?',
        answer:'The design pattern used by Java for all Swing components is the Model View Controller (MVC) pattern.'}),
    (swingTopic)-[:contains]->(q78),
    (q78)-[:has]->(oracleCustomer),
    (q78)-[:has]->(javaTag)

create (q80:Question:_Question {question:'What is JDBC?',
        answer:'JDBC is an abstraction layer that allows users to choose between databases. JDBC enables developers to write database applications in Java, without having to concern themselves with the underlying details of a particular database.'}),
    (jdbcTopic)-[:contains]->(q80),
    (q80)-[:has]->(sunCustomer),
    (q80)-[:has]->(dataTag)

create (q81:Question:_Question {question:'Explain the role of Driver in JDBC.',
        answer:'The JDBC Driver provides vendor-specific implementations of the abstract classes provided by the JDBC API. Each driver must provide implementations for the following classes of the java.sql package:Connection, Statement, PreparedStatement, CallableStatement, ResultSet and Driver.'}),
    (jdbcTopic)-[:contains]->(q81),
    (q81)-[:has]->(classTag)

create (q82:Question:_Question {question:'What is the purpose Class.forName method?',
        answer:'This method is used to method is used to load the driver that will establish a connection to the database.'}),
    (jdbcTopic)-[:contains]->(q82),
    (q82)-[:has]->(microsoftCustomer),
    (q82)-[:has]->(methodTag),
    (q82)-[:has]->(classTag)

create (q83:Question:_Question {question:'What is the advantage of PreparedStatement over Statement?',
        answer:'PreparedStatements are precompiled and thus, their performance is much better. Also, PreparedStatement objects can be reused with different input values to their queries.'}),
    (jdbcTopic)-[:contains]->(q83),
    (q83)-[:has]->(sapCustomer)

create (q84:Question:_Question {question:'What is the use of CallableStatement?',
        answer:'Name the method, which is used to prepare a CallableStatement. A CallableStatement is used to execute stored procedures. Stored procedures are stored and offered by a database. Stored procedures may take input values from the user and may return a result. The usage of stored procedures is highly encouraged, because it offers security and modularity.The method that prepares a CallableStatement is the following:
    CallableStament.prepareCall();'}),
    (jdbcTopic)-[:contains]->(q84),
    (q84)-[:has]->(jonsonNJonsonCustomer),
    (q84)-[:has]->(methodTag)

create (q85:Question:_Question {question:'What does Connection pooling mean?',
        answer:'The interaction with a database can be costly, regarding the opening and closing of database connections. Especially, when the number of database clients increases, this cost is very high and a large number of resources is consumed.A pool of database connections is obtained at start up by the application server and is maintained in a pool. A request for a connection is served by a connection residing in the pool. In the end of the connection, the request is returned to the pool and can be used to satisfy future requests.'}),
    (jdbcTopic)-[:contains]->(q85),
    (q85)-[:has]->(foxConnCustomer),
    (q85)-[:has]->(requestTag),
    (q85)-[:has]->(dataTag)

create (q87:Question:_Question {question:'What is RMI?',
        answer:'The Java Remote Method Invocation (Java RMI) is a Java API that performs the object-oriented equivalent of remote procedure calls (RPC), with support for direct transfer of serialized Java classes and distributed garbage collection. Remote Method Invocation (RMI) can also be seen as the process of activating a method on a remotely running object. RMI offers location transparency because a user feels that a method is executed on a locally running object.'}),
    (rmiTopic)-[:contains]->(q87),
    (q87)-[:has]->(ibmCustomer),
    (q87)-[:has]->(javaTag),
    (q87)-[:has]->(methodTag),
    (q87)-[:has]->(objectTag),
    (q87)-[:has]->(remoteTag),
    (q87)-[:has]->(rmiTag)

create (q88:Question:_Question {question:'What is the basic principle of RMI architecture?',
        answer:'The RMI architecture is based on a very important principle which states that the definition of the behavior and the implementation of that behavior, are separate concepts. RMI allows the code that defines the behavior and the code that implements the behavior to remain separate and to run on separate JVMs.'}),
    (rmiTopic)-[:contains]->(q88),
    (q88)-[:has]->(rmiTag),
    (q88)-[:has]->(codeTag)

create (q89:Question:_Question {question:'What are the layers of RMI Architecture?',
        answer:'The RMI architecture consists of the following layers:

    Stub and Skeleton layer: This layer lies just beneath the view of the developer. This layer is responsible for intercepting method calls made by the client to the interface and redirect these calls to a remote RMI Service.
    Remote Reference Layer: The second layer of the RMI architecture deals with the interpretation of references made from the client to the server’s remote objects. This layer interprets and manages references made from clients to the remote service objects. The connection is a one-to-one (unicast) link.
    Transport layer: This layer is responsible for connecting the two JVM participating in the service. This layer is based on TCP/IP connections between machines in a network. It provides basic connectivity, as well as some firewall penetration strategies.
'}),
    (rmiTopic)-[:contains]->(q89),
    (q89)-[:has]->(msiCustomer),
    (q89)-[:has]->(objectTag),
    (q89)-[:has]->(remoteTag),
    (q89)-[:has]->(rmiTag)

create (q90:Question:_Question {question:'What is the role of Remote Interface in RMI?',
        answer:'The Remote interface serves to identify interfaces whose methods may be invoked from a non-local virtual machine. Any object that is a remote object must directly or indirectly implement this interface. A class that implements a remote interface should declare the remote interfaces being implemented, define the constructor for each remote object and provide an implementation for each remote method in all remote interfaces.'}),
    (rmiTopic)-[:contains]->(q90),
    (q90)-[:has]->(gigabyteCustomer),
    (q90)-[:has]->(methodTag),
    (q90)-[:has]->(objectTag),
    (q90)-[:has]->(remoteTag),
    (q90)-[:has]->(interfaceTag),
    (q90)-[:has]->(rmiTag)

create (q91:Question:_Question {question:'What is the role of the java.rmi.Naming Class?',
        answer:'The java.rmi.Naming class provides methods for storing and obtaining references to remote objects in the remote object registry. Each method of the Naming class takes as one of its arguments a name that is a String in URL format.'}),
    (rmiTopic)-[:contains]->(q91),
    (q91)-[:has]->(javaTag),
    (q91)-[:has]->(methodTag),
    (q91)-[:has]->(classTag),
    (q91)-[:has]->(objectTag),
    (q91)-[:has]->(remoteTag),
    (q91)-[:has]->(rmiTag)

create (q92:Question:_Question {question:'What is meant by binding in RMI?',
        answer:'Binding is the process of associating or registering a name for a remote object, which can be used at a later time, in order to look up that remote object. A remote object can be associated with a name using the bind or rebind methods of the Naming class.'}),
    (rmiTopic)-[:contains]->(q92),
    (q92)-[:has]->(nVidiaCustomer),
    (q92)-[:has]->(objectTag),
    (q92)-[:has]->(remoteTag),
    (q92)-[:has]->(rmiTag)

create (q93:Question:_Question {question:'What is the difference between using bind() and rebind() methods of Naming Class?',
        answer:'The bind method bind is responsible for binding the specified name to a remote object, while the rebind method is responsible for rebinding the specified name to a new remote object. In case a binding exists for that name, the binding is replaced.'}),
    (rmiTopic)-[:contains]->(q93),
    (q93)-[:has]->(msiCustomer),
    (q93)-[:has]->(methodTag),
    (q93)-[:has]->(classTag),
    (q93)-[:has]->(objectTag),
    (q93)-[:has]->(remoteTag)

create (q94:Question:_Question {question:'What are the steps involved to make work a RMI program?',
        answer:'The following steps must be involved in order for a RMI program to work properly:

    Compilation of all source files.
    Generatation of the stubs using rmic.
    Start the rmiregistry.
    Start the RMIServer.
    Run the client program.
'}),
    (rmiTopic)-[:contains]->(q94),
    (q94)-[:has]->(amdCustomer),
    (q94)-[:has]->(rmiTag)

create (q95:Question:_Question {question:'What is the role of stub in RMI?',
        answer:'A stub for a remote object acts as a client’s local representative or proxy for the remote object. The caller invokes a method on the local stub, which is responsible for executing the method on the remote object. When a stub’s method is invoked, it undergoes the following steps:

    It initiates a connection to the remote JVM containing the remote object.
    It marshals the parameters to the remote JVM.
    It waits for the result of the method invocation and execution.
    It unmarshals the return value or an exception if the method has not been successfully executed.
    It returns the value to the caller.
'}),
    (rmiTopic)-[:contains]->(q95),
    (q95)-[:has]->(methodTag),
    (q95)-[:has]->(objectTag),
    (q95)-[:has]->(remoteTag),
    (q95)-[:has]->(rmiTag),
    (q95)-[:has]->(jvmTag)

create (q96:Question:_Question {question:'What is DGC? And how does it work?',
        answer:'DGC stands for Distributed Garbage Collection. Remote Method Invocation (RMI) uses DGC for automatic garbage collection. Since RMI involves remote object references across JVM’s, garbage collection can be quite difficult. DGC uses a reference counting algorithm to provide automatic memory management for remote objects.'}),
    (rmiTopic)-[:contains]->(q96),
    (q96)-[:has]->(objectTag),
    (q96)-[:has]->(collectionTag),
    (q96)-[:has]->(remoteTag),
    (q96)-[:has]->(garbageTag),
    (q96)-[:has]->(rmiTag)

create (q97:Question:_Question {question:'What is the purpose of using RMISecurityManager in RMI?',
        answer:'RMISecurityManager provides a security manager that can be used by RMI applications, which use downloaded code. The class loader of RMI will not download any classes from remote locations, if the security manager has not been set.'}),
    (rmiTopic)-[:contains]->(q97),
    (q97)-[:has]->(msiCustomer),
    (q97)-[:has]->(classTag),
    (q97)-[:has]->(rmiTag),
    (q97)-[:has]->(loadTag)

create (q98:Question:_Question {question:'Explain Marshalling and demarshalling.',
        answer:'When an application wants to pass its memory objects across a network to another host or persist it to storage, the in-memory representation must be converted to a suitable format. This process is called marshalling and the revert operation is called demarshalling.'}),
    (rmiTopic)-[:contains]->(q98),
    (q98)-[:has]->(sapCustomer)

create (q99:Question:_Question {question:'Explain Serialization and Deserialization.',
        answer:'Java provides a mechanism, called object serialization where an object can be represented as a sequence of bytes and includes the object’s data, as well as information about the object’s type, and the types of data stored in the object. Thus, serialization can be seen as a way of flattening objects, in order to be stored on disk, and later, read back and reconstituted. Deserialisation is the reverse process of converting an object from its flattened state to a live object.'}),
    (rmiTopic)-[:contains]->(q99),
    (q99)-[:has]->(gigabyteCustomer),
    (q99)-[:has]->(objectTag),
    (q99)-[:has]->(dataTag)

create (q101:Question:_Question {question:'What is a Servlet?',
        answer:'The servlet is a Java programming language class used to process client requests and generate dynamic web content. Servlets are mostly used to process or store data submitted by an HTML form, provide dynamic content and manage state information that does not exist in the stateless HTTP protocol.'}),
    (servletsTopic)-[:contains]->(q101),
    (q101)-[:has]->(servletTag)

create (q102:Question:_Question {question:'Explain the architechure of a Servlet.',
        answer:'The core abstraction that must be implemented by all servlets is the javax.servlet.Servlet interface. Each servlet must implement it either directly or indirectly, either by extending javax.servlet.GenericServlet or javax.servlet.http.HTTPServlet. Finally, each servlet is able to serve multiple requests in parallel using multithreading.'}),
    (servletsTopic)-[:contains]->(q102),
    (q102)-[:has]->(jonsonNJonsonCustomer),
    (q102)-[:has]->(javaTag),
    (q102)-[:has]->(servletTag),
    (q102)-[:has]->(httpTag)

create (q103:Question:_Question {question:'What is the difference between an Applet and a Servlet?',
        answer:'An Applet is a client side java program that runs within a Web browser on the client machine. On the other hand, a servlet is a server side component that runs on the web server.An applet can use the user interface classes, while a servlet does not have a user interface. Instead, a servlet waits for client’s HTTP requests and generates a response in every request.'}),
    (servletsTopic)-[:contains]->(q103),
    (q103)-[:has]->(gigabyteCustomer),
    (q103)-[:has]->(servletTag),
    (q103)-[:has]->(appletTag),
    (q103)-[:has]->(interfaceTag),
    (q103)-[:has]->(requestTag),
    (q103)-[:has]->(webTag),
    (q103)-[:has]->(serverTag)

create (q104:Question:_Question {question:'What is the difference between GenericServlet and HttpServlet?',
        answer:'GenericServlet is a generalized and protocol-independent servlet that implements the Servlet and ServletConfig interfaces. Those servlets extending the GenericServlet class shall override the service method. Finally, in order to develop an HTTP servlet for use on the Web that serves requests using the HTTP protocol, your servlet must extend the HttpServlet instead.'}),
    (servletsTopic)-[:contains]->(q104),
    (q104)-[:has]->(servletTag),
    (q104)-[:has]->(httpTag)

create (q105:Question:_Question {question:'Explain the life cycle of a Servlet.',
        answer:'On every client’s request, the Servlet Engine loads the servlets and invokes its init methods, in order for the servlet to be initialized. Then, the Servlet object handles all subsequent requests coming from that client, by invoking the service method for each request separately. Finally, the servlet is removed by calling the server’s destroy method.'}),
    (servletsTopic)-[:contains]->(q105),
    (q105)-[:has]->(jonsonNJonsonCustomer),
    (q105)-[:has]->(methodTag),
    (q105)-[:has]->(servletTag),
    (q105)-[:has]->(requestTag)

create (q106:Question:_Question {question:'What is the difference between doGet() and doPost()?',
        answer:'doGET: The GET method appends the name-value pairs on the request’s URL. Thus, there is a limit on the number of characters and subsequently on the number of values that can be used in a client’s request. Furthermore, the values of the request are made visible and thus, sensitive information must not be passed in that way. doPOST: The POST method overcomes the limit imposed by the GET request, by sending the values of the request inside its body. Also, there is no limitations on the number of values to be sent across. Finally, the sensitive information passed through a POST request is not visible to an external client.'}),
    (servletsTopic)-[:contains]->(q106),
    (q106)-[:has]->(gigabyteCustomer),
    (q106)-[:has]->(methodTag),
    (q106)-[:has]->(requestTag)

create (q107:Question:_Question {question:'What is meant by a Web Application?',
        answer:'A Web application is a dynamic extension of a Web or application server. There are two types of web applications: presentation-oriented and service-oriented. A presentation-oriented Web application generates interactive web pages, which contain various types of markup language and dynamic content in response to requests. On the other hand, a service-oriented web application implements the endpoint of a web service. In general, a Web application can be seen as a collection of servlets installed under a specific subset of the server’s URL namespace.'}),
    (servletsTopic)-[:contains]->(q107),
    (q107)-[:has]->(webTag),
    (q107)-[:has]->(applicationTag),
    (q107)-[:has]->(serverTag)

create (q108:Question:_Question {question:'What is a Server Side Include (SSI)?',
        answer:'Server Side Includes (SSI) is a simple interpreted server-side scripting language, used almost exclusively for the Web, and is embedded with a servlet tag. The most frequent use of SSI is to include the contents of one or more files into a Web page on a Web server. When a Web page is accessed by a browser, the Web server replaces the servlet tag in that Web page with the hyper text generated by the corresponding servlet.'}),
    (servletsTopic)-[:contains]->(q108),
    (q108)-[:has]->(atiCustomer),
    (q108)-[:has]->(servletTag),
    (q108)-[:has]->(webTag),
    (q108)-[:has]->(serverTag)

create (q109:Question:_Question {question:'What is Servlet Chaining?',
        answer:'Servlet Chaining is the method where the output of one servlet is sent to a second servlet. The output of the second servlet can be sent to a third servlet, and so on. The last servlet in the chain is responsible for sending the response to the client.'}),
    (servletsTopic)-[:contains]->(q109),
    (q109)-[:has]->(nVidiaCustomer),
    (q109)-[:has]->(servletTag)

create (q110:Question:_Question {question:'How do you find out what client machine is making a request to your servlet?',
        answer:'The ServletRequest class has functions for finding out the IP address or host name of the client machine. getRemoteAddr() gets the IP address of the client machine and getRemoteHost() gets the host name of the client machine. See example here.'}),
    (servletsTopic)-[:contains]->(q110),
    (q110)-[:has]->(googleCustomer),
    (q110)-[:has]->(servletTag),
    (q110)-[:has]->(remoteTag),
    (q110)-[:has]->(requestTag)

create (q111:Question:_Question {question:'What is the structure of the HTTP response?',
        answer:'The HTTP response consists of three parts:

    Status Code: describes the status of the response. It can be used to check if the request has been successfully completed. In case the request failed, the status code can be used to find out the reason behind the failure. If your servlet does not return a status code, the success status code, HttpServletResponse.SC_OK, is returned by default.
    HTTP Headers: they contain more information about the response. For example, the headers may specify the date/time after which the response is considered stale, or the form of encoding used to safely transfer the entity to the user. See how to retrieve headers in Servlet here.
    Body: it contains the content of the response. The body may contain HTML code, an image, etc. The body consists of the data bytes transmitted in an HTTP transaction message immediately following the headers.
'}),
    (servletsTopic)-[:contains]->(q111),
    (q111)-[:has]->(hpCustomer),
    (q111)-[:has]->(servletTag),
    (q111)-[:has]->(requestTag),
    (q111)-[:has]->(codeTag),
    (q111)-[:has]->(httpTag)

create (q112:Question:_Question {question:'What is a cookie? What is the difference between session and cookie?',
        answer:'A cookie is a bit of information that the Web server sends to the browser. The browser stores the cookies for each Web server in a local file. In a future request, the browser, along with the request, sends all stored cookies for that specific Web server.The differences between session and a cookie are the following:

    The session should work, regardless of the settings on the client browser. The client may have chosen to disable cookies. However, the sessions still work, as the client has no ability to disable them in the server side.
    The session and cookies also differ in the amount of information the can store. The HTTP session is capable of storing any Java object, while a cookie can only store String objects.
'}),
    (servletsTopic)-[:contains]->(q112),
    (q112)-[:has]->(objectTag),
    (q112)-[:has]->(requestTag),
    (q112)-[:has]->(webTag),
    (q112)-[:has]->(serverTag),
    (q112)-[:has]->(browserTag)

create (q113:Question:_Question {question:'Which protocol will be used by browser and servlet to communicate?',
        answer:'The browser communicates with a servlet by using the HTTP protocol.'}),
    (servletsTopic)-[:contains]->(q113),
    (q113)-[:has]->(oracleCustomer),
    (q113)-[:has]->(servletTag),
    (q113)-[:has]->(browserTag)

create (q114:Question:_Question {question:'What is HTTP Tunneling?',
        answer:'HTTP Tunneling is a technique by which, communications performed using various network protocols are encapsulated using the HTTP or HTTPS protocols. The HTTP protocol therefore acts as a wrapper for a channel that the network protocol being tunneled uses to communicate. The masking of other protocol requests as HTTP requests is HTTP Tunneling.'}),
    (servletsTopic)-[:contains]->(q114),
    (q114)-[:has]->(gigabyteCustomer),
    (q114)-[:has]->(requestTag),
    (q114)-[:has]->(httpTag)

create (q115:Question:_Question {question:'What’s the difference between sendRedirect and forward methods?',
        answer:'The sendRedirect method creates a new request, while the forward method just forwards a request to a new target. The previous request scope objects are not available after a redirect, because it results in a new request. On the other hand, the previous request scope objects are available after forwarding. FInally, in general, the sendRedirect method is considered to be slower compare to the forward method.'}),
    (servletsTopic)-[:contains]->(q115),
    (q115)-[:has]->(ibmCustomer),
    (q115)-[:has]->(methodTag),
    (q115)-[:has]->(objectTag),
    (q115)-[:has]->(requestTag)

create (q116:Question:_Question {question:'What is URL Encoding and URL Decoding?',
        answer:'The URL encoding procedure is responsible for replacing all the spaces and every other extra special character of a URL, into their corresponding Hex representation. In correspondence, URL decoding is the exact opposite procedure.'}),
    (servletsTopic)-[:contains]->(q116)

create (q118:Question:_Question {question:'What is a JSP Page?',
        answer:'A Java Server Page (JSP) is a text document that contains two types of text: static data and JSP elements. Static data can be expressed in any text-based format, such as HTML or XML. JSP is a technology that mixes static content with dynamically-generated content. See JSP example here.'}),
    (jspTopic)-[:contains]->(q118),
    (q118)-[:has]->(jspTag),
    (q118)-[:has]->(dataTag)

create (q119:Question:_Question {question:'How are the JSP requests handled?',
        answer:'On the arrival of a JSP request, the browser first requests a page with a .jsp extension. Then, the Web server reads the request and using the JSP compiler, the Web server converts the JSP page into a servlet class. Notice that the JSP file is compiled only on the first request of the page, or if the JSP file has changed.The generated servlet class is invoked, in order to handle the browser’s request. Once the execution of the request is over, the servlet sends a response back to the client. See how to get Request parameters in a JSP.'}),
    (jspTopic)-[:contains]->(q119),
    (q119)-[:has]->(microsoftCustomer),
    (q119)-[:has]->(classTag),
    (q119)-[:has]->(servletTag),
    (q119)-[:has]->(jspTag),
    (q119)-[:has]->(requestTag),
    (q119)-[:has]->(webTag),
    (q119)-[:has]->(serverTag),
    (q119)-[:has]->(browserTag),
    (q119)-[:has]->(fileTag)

create (q120:Question:_Question {question:'What are the advantages of JSP?',
        answer:'The advantages of using the JSP technology are shown below:

    JSP pages are dynamically compiled into servlets and thus, the developers can easily make updates to presentation code.
    JSP pages can be pre-compiled.
    JSP pages can be easily combined to static templates, including HTML or XML fragments, with code that generates dynamic content.
    Developers can offer customized JSP tag libraries that page authors access using an XML-like syntax.
    Developers can make logic changes at the component level, without editing the individual pages that use the application’s logic.
'}),
    (jspTopic)-[:contains]->(q120),
    (q120)-[:has]->(nVidiaCustomer),
    (q120)-[:has]->(jspTag),
    (q120)-[:has]->(codeTag)

create (q121:Question:_Question {question:'What are Directives? What are the different types of Directives available in JSP?',
        answer:'Directives are instructions that are processed by the JSP engine, when the page is compiled to a servlet. Directives are used to set page-level instructions, insert data from external files, and specify custom tag libraries. Directives are defined between < %@ and % >.The different types of directives are shown below:

    Include directive: it is used to include a file and merges the content of the file with the current page.
    Page directive: it is used to define specific attributes in the JSP page, like error page and buffer.
    Taglib: it is used to declare a custom tag library which is used in the page.
'}),
    (jspTopic)-[:contains]->(q121),
    (q121)-[:has]->(oracleCustomer),
    (q121)-[:has]->(jspTag),
    (q121)-[:has]->(fileTag)

create (q122:Question:_Question {question:'What are JSP actions?',
        answer:'JSP actions use constructs in XML syntax to control the behavior of the servlet engine. JSP actions are executed when a JSP page is requested. They can be dynamically inserted into a file, re-use JavaBeans components, forward the user to another page, or generate HTML for the Java plugin.Some of the available actions are listed below:

    jsp:include – includes a file, when the JSP page is requested.
    jsp:useBean – finds or instantiates a JavaBean.
    jsp:setProperty – sets the property of a JavaBean.
    jsp:getProperty – gets the property of a JavaBean.
    jsp:forward – forwards the requester to a new page.
    jsp:plugin – generates browser-specific code.
'}),
    (jspTopic)-[:contains]->(q122),
    (q122)-[:has]->(msiCustomer),
    (q122)-[:has]->(javaTag),
    (q122)-[:has]->(jspTag),
    (q122)-[:has]->(requestTag),
    (q122)-[:has]->(fileTag)

create (q123:Question:_Question {question:'What are Scriptlets?',
        answer:'In Java Server Pages (JSP) technology, a scriptlet is a piece of Java-code embedded in a JSP page. The scriptlet is everything inside the tags. Between these tags, a user can add any valid scriplet.'}),
    (jspTopic)-[:contains]->(q123),
    (q123)-[:has]->(sapCustomer),
    (q123)-[:has]->(javaTag),
    (q123)-[:has]->(jspTag)

create (q124:Question:_Question {question:'What are Decalarations?',
        answer:'Declarations are similar to variable declarations in Java. Declarations are used to declare variables for subsequent use in expressions or scriptlets. To add a declaration, you must use the sequences to enclose your declarations.'}),
    (jspTopic)-[:contains]->(q124)

create (q125:Question:_Question {question:'What are Expressions?',
        answer:'A JSP expression is used to insert the value of a scripting language expression, converted into a string, into the data stream returned to the client, by the web server. Expressions are defined between <% = and %> tags.'}),
    (jspTopic)-[:contains]->(q125),
    (q125)-[:has]->(sunCustomer)

create (q126:Question:_Question {question:'What is meant by implicit objects and what are they?',
        answer:'JSP implicit objects are those Java objects that the JSP Container makes available to developers in each page. A developer can call them directly, without being explicitly declared. JSP Implicit Objects are also called pre-defined variables.The following objects are considered implicit in a JSP page:

    application
    page
    request
    response
    session
    exception
    out
    config
    pageContext'}),
    (jspTopic)-[:contains]->(q126),
    (q126)-[:has]->(tutbyCustomer),
    (q126)-[:has]->(objectTag),
    (q126)-[:has]->(jspTag)
