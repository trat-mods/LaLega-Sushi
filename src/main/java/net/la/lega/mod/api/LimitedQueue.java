package net.la.lega.mod.api;

import java.util.ArrayList;

public class LimitedQueue<T>
{
    private ArrayList<T> queue;
    private int bound;
    
    public LimitedQueue(int bound)
    {
        this.bound = bound;
        queue = new ArrayList<>();
    }
    
    public int size()
    {
        return queue.size();
    }
    
    public boolean canAdd()
    {
        return size() < bound;
    }
    
    public boolean isEmpty()
    {
        return size() <= 0;
    }
    
    public boolean contains(Object o)
    {
        int size = size();
        for(int i = 0; i < size; i++)
        {
            if(o.equals(queue.get(i)))
            {
                return true;
            }
        }
        return false;
    }
    
    public void clear()
    {
        queue.clear();
    }
    
    public boolean enqueue(T elem)
    {
        if(canAdd())
        {
            queue.add(elem);
            return true;
        }
        return false;
    }
    
    public T poll()
    {
        if(isEmpty()) return null;
        return queue.remove(size() - 1);
    }
    
    public T at(int index)
    {
        if(index >= size() || index < 0) return null;
        return queue.get(index);
    }
    
    public T head()
    {
        if(isEmpty())
        {
            return null;
        }
        return queue.get(size() - 1);
    }
}
