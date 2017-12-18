package com.zjm.binderaidlservice;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zhujiaming on 2017/12/18.
 */

public class BookManagerStub extends IBookManager.Stub{

    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnBookListChangedListener> listeners = new RemoteCallbackList<>();

    public BookManagerStub() {
    }

    @Override
    public void addBook(Book book) throws RemoteException {
        //运行在服务端的Binder线程池中的线程
        mBooks.add(book);
        broadCast();
    }

    @Override
    public List<Book> getBookList() throws RemoteException {
        return mBooks;
    }

    @Override
    public Book getBookById(int id) throws RemoteException {
        for (Book book : mBooks) {
            if (book.getBookId() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public boolean delBookById(int id) throws RemoteException {
        for (int i = 0; i < mBooks.size(); i++) {
            Book book = mBooks.get(i);
            if (book.getBookId() == id) {
                mBooks.remove(i);
                broadCast();
                return true;
            }
        }
        return false;
    }

    @Override
    public void registListener(IOnBookListChangedListener listener) throws RemoteException {
        listeners.register(listener);
        BookManagerService.hint("客户端注册监听成功,"+listeners.getRegisteredCallbackCount());
    }

    @Override
    public void unRegistListener(IOnBookListChangedListener listener) throws RemoteException {
        listeners.unregister(listener);
        BookManagerService.hint("客户端取消监听成功,"+listeners.getRegisteredCallbackCount());
    }

    private void broadCast() {
        int N = listeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                IOnBookListChangedListener listener = listeners.getBroadcastItem(i);
                if (listener != null) {
                    listener.onChanged(mBooks);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        listeners.finishBroadcast();
    }
}
