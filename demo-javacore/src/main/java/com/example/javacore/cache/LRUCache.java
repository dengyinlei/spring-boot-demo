package com.example.javacore.cache;

import java.util.HashMap;

/**
 * @description : 
 * @author : Denley
 * @date : 2022/3/4 18:44
 **/
public class LRUCache {
    //缓存容量
    private int cap;
    //快速查询
    private HashMap<Integer,Node> key2Node;
    //双向链表
    private DoubleList doubleList;
    public LRUCache(int capacity) {
        this.cap = cap;
        this.key2Node = new HashMap<>();
        this.doubleList = new DoubleList();
    }


    public int get(int key) {
        if (!key2Node.containsKey(key)) {
            return -1;
        }
        //包含key, 则将其重新放入第一位, 之后返回结果
        Node temp = key2Node.get(key);
        doubleList.remove(temp);
        doubleList.addFirst(temp);
        return temp.value;
    }


    public void put(int key, int value) {
        Node node = new Node(key, value);
        //包含key, 将老的移除,并将新节点放入头部
        if (key2Node.containsKey(key)) {
            Node temp = key2Node.get(key);
            doubleList.remove(temp);
            doubleList.addFirst(node);
            //务必添加key与node对应关系
            key2Node.put(key, node);
            return;
        }
        //不包含key
        //容量满了,移除尾部节点
        if (cap == doubleList.size || cap == key2Node.size()) {
            Node last = doubleList.removeList();
            ////务必移除对应关系
            key2Node.remove(last.key);
        }
       //添加新节点到头部, 并务必维护key和node对应关系
        doubleList.addFirst(node);
        key2Node.put(key, node);
    }




 //链表节点
 class Node{

        //key和value都需要,因为需要通过key去map中获取节点
        private int key;
        private int value;
        //下一个节点
        private Node next;
        //前一个节点, (因为节点可能被删除,所以需要知道他的前一个节点,用来将前后两个节点连接)
        private Node prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
 //双向链表
    class DoubleList{
        //头结点
     Node head = new Node(0, 0);
     //尾结点
     Node tail = new Node(0, 0);
     int size;
     private DoubleList() {
        head.next = tail;
        tail.prev = head;
        size = 0;
     }

     /**
      * 给头部添加节点
      * 仅仅操作当前节点以及相邻的节点 o(1)
      */
     private void addFirst(Node x){
        //将head的下一个节点取出
         Node headNext = head.next;
         //将头结点的next指向 x
         head.next = x;
         // x节点的上一个节点指向head
         x.prev = head;

         // x节点的下一个节点指向headNext
         x.next = headNext;

        // headNext的前一个节点指向x
         headNext.prev = x;
         size++;
     }


     //移除指定节点
     //仅操作当前节点及相邻两个节点, O(1)
     private void remove(Node x) {
         if (x == null) {
             return;
         }
         //将被移除节点的上一个节点的next, 指向被移除节点的下一个节点
         x.prev.next = x.next;

         //将被移除节点的下一个节点的prev, 指向被移除节点的上一个节点
         x.next.prev = x.prev;
         size--;
     }


     /**
      * 获取并移除尾结点
      */
     private Node removeList(){
         Node last = tail.prev;
         remove(last);
         size --;
         return last;
     }
 }

}
