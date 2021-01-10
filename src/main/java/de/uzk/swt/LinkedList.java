package de.uzk.swt;

import java.util.*;

/**
 * Simple LinkedList-Implementation, die als Komponente im Beispielprogramm verwendet wird.
 * Mir ist klar dass es eine {@link java.util.LinkedList} in der Standardbibliothek gibt,
 * und ich erwarte auch nicht dass Sie diesen Code in der Übungszeit komplett nachvollziehen.
 * Lesen Sie sich einfach die Dokumentation des {@link List} Interfaces durch, um zu verstehen
 * was das Ergebnis jeder Operation sein sollte.
 *
 * @param <T> Typ der Objekte, die in der Liste gespeichert werden sollen
 */
public class LinkedList<T> implements List<T> {
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            ListNode<T> cursor = head;

            @Override
            public boolean hasNext() {
                return cursor != null;
            }

            @Override
            public T next() {
                T it = cursor.item;
                cursor = cursor.next;
                return it;
            }
        };
    }

    public Object[] toArray() {
        ListNode<T> cursor = head;
        Object[] arr = new Object[size];
        for(int i = 0; i < size; i++, cursor = cursor.next) {
            arr[i] = cursor.item;
        }
        return arr;
    }

    public <T1> T1[] toArray(T1[] a) {
        ListNode<T> cursor = head;
        for(int i = 0; i < a.length; i++, cursor = cursor.next) {
            a[i] = (T1) cursor.item;
        }
        return a;
    }

    /**
     * Platziert die übergebene Kette von {@link ListNode ListNodes} nach dem angegebenen {@code cursor}.
     *
     * @param cursor Der Knoten, hinter dem die Kette eingefügt werden soll. Ist dies {@code null},
     *               wird die Kette am Anfang der Liste eingefügt
     * @param chainStart Start der Kette
     * @param chainEnd Ende der Kette
     * @return {@code true}: es sollte nicht möglich sein, dass diese Operation scheitert.
     */
    private boolean insertChain(ListNode<T> cursor, ListNode<T> chainStart, ListNode<T> chainEnd) {
        ListNode<T> next = head;
        if(cursor != null)
            next = cursor.next;

        if(cursor == null) {
            head = chainStart;
            chainStart.prev = null;
        } else {
            cursor.next = chainStart;
            chainStart.prev = cursor;
        }
        if(next == null) {
            tail = chainEnd;
            chainEnd.next = null;
        } else {
            next.prev = chainEnd;
            chainEnd.next = next;
        }
        return true;
    }

    private boolean add(ListNode<T> cursor, T item) {
        ListNode<T> newNode = new ListNode<>(null, null, item);
        return insertChain(cursor, newNode, newNode);
    }

    public boolean add(T t) {
        return add(tail, t);
    }

    private boolean remove(ListNode<T> cursor) {
        ListNode<T> prev = cursor.prev;
        ListNode<T> next = cursor.next;
        if(prev == null)
            head = next;
        else
            prev.next = next;
        if(next == null)
            tail = prev;
        else
            next.prev = prev;
        return true;
    }

    public boolean remove(Object o) {
        ListNode<T> cursor = head;
        while(cursor != null) {
            if(Objects.equals(cursor.item, o)) {
                if(remove(cursor))
                    return true;
            }
            cursor = cursor.next;
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        for(Object e : c) {
            if(!contains(e))
                return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        boolean somethingChanged = false;
        for(T e : c) {
            if(add(e))
                somethingChanged = true;
        }
        return somethingChanged;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        ListNode<T> chainStart = null;
        ListNode<T> chainEnd = null;
        for(T element : c) {
            ListNode<T> newNode = new ListNode<>(chainEnd, null, element);
            if(chainStart == null)
                chainStart = newNode;
            if(chainEnd != null)
                chainEnd.next = newNode;
            chainEnd = newNode;
        }

        if(index == 0)
            return insertChain(null, chainStart, chainEnd);

        ListNode<T> cursor = head;
        for(int i = 1; i < index; i++) {
            cursor = cursor.next;
        }
        return insertChain(cursor, chainStart, chainEnd);
    }

    public boolean removeAll(Collection<?> c) {
        ListNode<T> cursor = head;
        boolean somethingChanged = false;
        while(cursor != null) {
            if(c.contains(cursor.item)) {
                if(remove(cursor))
                    somethingChanged = true;
            }
            cursor = cursor.next;
        }
        return somethingChanged;
    }

    public boolean retainAll(Collection<?> c) {
        ListNode<T> cursor = head;
        boolean somethingChanged = false;
        while(cursor != null) {
            if(!c.contains(cursor.item)) {
                if(remove(cursor))
                    somethingChanged = true;
            }
            cursor = cursor.next;
        }
        return somethingChanged;
    }

    public void clear() {
        // TODO: This probably causes a memory leak
        head = null;
        tail = null;
        size = 0;
    }

    public T get(int index) {
        ListNode<T> cursor = head;
        for(int i = 0; i < index; i++) {
            cursor = cursor.next;
        }
        return cursor.item;
    }

    public T set(int index, T element) {
        ListNode<T> cursor = head;
        for(int i = 0; i < index; i++) {
            cursor = cursor.next;
        }
        T old = cursor.item;
        cursor.item = element;
        return old;
    }

    public void add(int index, T element) {
        if(index == 0) {
            add(null, element);
            return;
        }
        ListNode<T> cursor = head;
        for(int i = 1; i < index; i++) {
            cursor = cursor.next;
        }
        add(cursor, element);
    }

    public T remove(int index) {
        ListNode<T> cursor = head;
        for(int i = 0; i < index; i++) {
            cursor = cursor.next;
        }
        T old = cursor.item;
        remove(cursor);
        return old;
    }

    public int indexOf(Object o) {
        ListNode<T> cursor = head;

        int i = -1;
        while(cursor != null) {
            i++;
            if(Objects.equals(cursor.item, o))
                return i;
            cursor = cursor.next;
        }
        return i;
    }

    public int lastIndexOf(Object o) {
        ListNode<T> cursor = tail;

        int i = size-1;
        while(cursor != null) {
            if(Objects.equals(cursor.item, o))
                return i;
            cursor = cursor.prev;
            i--;
        }
        return i;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new LinkedListIterator(null, 0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if(index == 0)
            return new LinkedListIterator(null, 0);
        ListNode<T> cursor = head;
        for(int i = 1; i < index; i++) {
            cursor = cursor.next;
        }
        return new LinkedListIterator(cursor, index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        ListNode<T> cursor = head;
        int i;
        for(i = 0; i < fromIndex; i++) {
            cursor = cursor.next;
        }
        ListNode<T> chainStart = null;
        ListNode<T> chainEnd = null;
        int count = 0;
        for(; i < toIndex && i < size; i++) {
            ListNode<T> newNode = new ListNode<>(chainEnd, null, cursor.item);
            if(chainStart == null)
                chainStart = newNode;
            if(chainEnd != null)
                chainEnd.next = newNode;
            chainEnd = newNode;
            cursor = cursor.next;
            count++;
        }
        LinkedList<T> newList = new LinkedList<>();
        newList.head = chainStart;
        newList.tail = chainEnd;
        newList.size = count;
        return newList;
    }

    private class LinkedListIterator implements ListIterator<T> {

        ListNode<T> cursor;
        int index;

        public LinkedListIterator(ListNode<T> cursor, int index) {
            this.cursor = cursor;
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            if(cursor == null)
                return head != null;
            return cursor.next != null;
        }

        @Override
        public T next() {
            if(cursor == null)
                cursor = head;
            else
                cursor = cursor.next;
            return cursor.item;
        }

        @Override
        public boolean hasPrevious() {
            if(cursor == null)
                return false;
            return cursor.prev != null;
        }

        @Override
        public T previous() {
            T value = cursor.item;
            cursor = cursor.prev;
            return value;
        }

        @Override
        public int nextIndex() {
            return Math.min(index+1, size);
        }

        @Override
        public int previousIndex() {
            return Math.max(index-1, -1);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }

    private int size;
    private ListNode<T> head;
    private ListNode<T> tail;

    /**
     * Einzelner Knoten der LinkedList. Die Funktionsweise einer LinkedList
     * wird in "Grundzüge der Informatik" erläutert.
     *
     * @param <T> Typ des Objekts, das in diesem Knoten gespeichert wird.
     */
    private static class ListNode<T> {
        private T item;
        private ListNode<T> prev;
        private ListNode<T> next;

        public ListNode(ListNode<T> prev, ListNode<T> next, T item) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
    }
}
