package org.smartregister.multitenant.check;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import org.smartregister.domain.FetchStatus;
import org.smartregister.multitenant.PreResetAppCheck;
import org.smartregister.multitenant.exception.PreResetAppOperationException;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.repository.EventClientRepository;
import org.smartregister.sync.intent.SyncIntentService;
import org.smartregister.view.activity.DrishtiApplication;

import java.util.List;

/**
 * Created by Ephraim Kigamba - nek.eam@gmail.com on 09-04-2020.
 */
public class EventClientSyncedCheck implements PreResetAppCheck, SyncStatusBroadcastReceiver.SyncStatusListener {

    public static final String UNIQUE_NAME = "EventClientSyncedCheck";

    @Override
    public boolean isCheckOk(@NonNull DrishtiApplication drishtiApplication) {
        return isEventsClientSynced(drishtiApplication);
    }

    @Override
    public void performPreResetAppOperations(@NonNull DrishtiApplication application) throws PreResetAppOperationException {
        /*SyncStatusBroadcastReceiver.init(application.getBaseContext());
        SyncStatusBroadcastReceiver syncStatusBroadcastReceiver = SyncStatusBroadcastReceiver.getInstance();

        syncStatusBroadcastReceiver.addSyncStatusListener(this);

        application.startService(new Intent(application.getApplicationContext(), SyncIntentService.class));*/

        PreResetSync syncIntentService = new PreResetSync();
        syncIntentService.performSync();
    }

    public boolean isEventsClientSynced(@NonNull DrishtiApplication application) {
        EventClientRepository eventClientRepository = application.getContext().getEventClientRepository();
        if (eventClientRepository != null) {
            return eventClientRepository.getUnSyncedEventsCount() == 0;
        }

        return false;
    }

    protected boolean isSyncServiceEnabled(@NonNull DrishtiApplication application) {
        final PackageManager packageManager = application.getPackageManager();
        final Intent intent = new Intent(application.getBaseContext(), SyncIntentService.class);
        List resolveInfo =
                packageManager.queryIntentServices(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onSyncStart() {
        // Do nothing for now
    }

    @Override
    public void onSyncInProgress(FetchStatus fetchStatus) {
        if (fetcatus == FetchStatus.fetchProgress) {

        }
    }

    @Override
    public void onSyncComplete(FetchStatus fetchStatus) {
        // Do nothing for now
    }

    @NonNull
    @Override
    public String getUniqueName() {
        return UNIQUE_NAME;
    }
}
