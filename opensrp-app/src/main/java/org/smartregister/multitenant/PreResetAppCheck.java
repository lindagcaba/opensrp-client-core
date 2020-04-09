package org.smartregister.multitenant;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import org.smartregister.multitenant.exception.PreResetAppOperationException;
import org.smartregister.view.activity.DrishtiApplication;

/**
 * Created by Ephraim Kigamba - nek.eam@gmail.com on 09-04-2020.
 */
public interface PreResetAppCheck {

    @WorkerThread
    boolean isCheckOk(@NonNull DrishtiApplication drishtiApplication);

    @WorkerThread
    void performPreResetAppOperations(@NonNull DrishtiApplication drishtiApplication) throws PreResetAppOperationException;

    @NonNull
    String getUniqueName();
}
