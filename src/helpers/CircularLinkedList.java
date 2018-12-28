package helpers;

public class CircularLinkedList<T> {

    private Node<T> currentNode = null;

    public CircularLinkedList() {

    }

    /**
     * Adds object between current and next item. CurrentNode is added node.
     * @param object T
     */
    public CircularLinkedList<T> addObject(T object) {
        if(currentNode == null) {
            addFirstObject(object);
            return this;
        }

        Node<T> nextNode = currentNode.getNextNode();
        Node<T> newNode = new Node<>(object, nextNode, currentNode);
        currentNode.setNextNode(newNode);
        nextNode.setPreviousNode(newNode);

        // Set created node as current node.
        currentNode = newNode;
        return this;
    }

    private void addFirstObject(T object) {
        Node<T> newNode = new Node<>(object);
        newNode.setPreviousNode(newNode);
        newNode.setNextNode(newNode);
        currentNode = newNode;
    }

    public CircularLinkedList<T> next() {
        if(currentNode == null) {
            return null;
        }
        currentNode = currentNode.getNextNode();
        return this;
    }

    public CircularLinkedList<T> next(int steps) {
        for(int i = 0; i < steps; i++) {
            next();
        }
        return this;
    }

    public CircularLinkedList<T> previous(int steps) {
        for(int i = 0; i < steps; i++) {
            previous();
        }
        return this;
    }


    public CircularLinkedList<T> previous() {
        if(currentNode == null) {
            return null;
        }
        currentNode = currentNode.getPreviousNode();
        return this;
    }

    public T getNext() {
        if(currentNode == null) {
            return null;
        }
        currentNode = currentNode.getNextNode();
        return currentNode.getObject();
    }

    public T getPrevious() {
        if(currentNode == null) {
            return null;
        }
        currentNode = currentNode.getPreviousNode();
        return currentNode.getObject();
    }



    /**
     * Next node is the new current node
     * @return T
     */
    public T removeCurrent() {
        Node<T> tempNode = currentNode;
        Node<T> previousNode = currentNode.getPreviousNode();
        Node<T> nextNode = currentNode.getNextNode();
        // Stich previous and next nodes together.
        previousNode.setNextNode(nextNode);
        nextNode.setPreviousNode(previousNode);
        currentNode = nextNode;
        return tempNode.getObject();
    }

    public T getCurrent() {
        return currentNode.object;
    }

    /**
     * This has the potential to break everything. Use in production code with exteme care.
     * @param object
     */
    public CircularLinkedList<T> setCurrent(Node<T> object) {
        currentNode = object;
        return this;
    }

    /**
     * This has the potential to break everything. Use in production code with exteme care.
     */
    public Node<T> getNode() {
        return currentNode;
    }

    /**
     * Update object for current items.
     * @param object T
     */
    public void updateObject(T object) {
        if(currentNode == null) {
            return;
        }
        currentNode.setObject(object);
    }

    public class Node<T> {
        private T object;
        private Node<T> nextNode;
        private Node<T> previousNode;

        Node(T object) {
            this.object = object;
        }

        Node(T object, Node nextNode, Node previousNode) {
            this(object);
            this.nextNode = nextNode;
            this.previousNode = previousNode;
        }

        public T getObject() {
            return object;
        }

        void setObject(T object) {
            this.object = object;
        }

        void setNextNode(Node<T> nextNode) {
            this.nextNode = nextNode;
        }

        void setPreviousNode(Node<T> previousNode) {
            this.previousNode = previousNode;
        }

        Node<T> getNextNode() {
            return nextNode;
        }

        Node<T> getPreviousNode() {
            return previousNode;
        }
    }
}
