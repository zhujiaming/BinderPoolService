package com.zjm.binderaidlservice;

import android.os.IBinder;
import android.os.RemoteException;

public  class BinderPoolImpl extends IBinderPool.Stub {
        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode) {
                case 0x0010:
                    return new BookManagerStub().asBinder();
                default:
                    return null;
            }

        }
    }