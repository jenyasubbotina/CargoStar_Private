package uz.alexits.cargostar.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import uz.alexits.cargostar.R;
import uz.alexits.cargostar.entities.calculation.CalculationResult;
import uz.alexits.cargostar.entities.calculation.Packaging;
import uz.alexits.cargostar.entities.calculation.PackagingAndPrice;
import uz.alexits.cargostar.entities.calculation.PackagingType;
import uz.alexits.cargostar.entities.calculation.Vat;
import uz.alexits.cargostar.entities.calculation.ZoneSettings;
import uz.alexits.cargostar.entities.location.Country;
import uz.alexits.cargostar.entities.transportation.Consignment;
import uz.alexits.cargostar.entities.calculation.Provider;
import uz.alexits.cargostar.repository.PackagingRepository;
import uz.alexits.cargostar.repository.ProviderRepository;
import uz.alexits.cargostar.repository.VatRepository;
import uz.alexits.cargostar.repository.ZoneRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CalculatorViewModel extends HeaderViewModel {
    protected final ProviderRepository providerRepository;
    protected final PackagingRepository packagingRepository;
    private final VatRepository vatRepository;
    private final ZoneRepository zoneRepository;

    /* countries */
    private final MutableLiveData<Long> selectedCountryId;
    private final MutableLiveData<Country> srcCountry;
    private final MutableLiveData<Long> srcCountryId;
    private final MutableLiveData<Country> destCountry;
    private final MutableLiveData<Long> destCountryId;

    protected long providerId;
    protected int type;
    private double totalWeight;
    private double totalVolume;
    private double totalPrice;

    /* providers */
    protected final MutableLiveData<Long> selectedProviderId;
    protected final MutableLiveData<Provider> selectedProvider;
    private final MediatorLiveData<List<Long>> providerIdList;

    /* packaging */
    protected final MutableLiveData<Integer> selectedType;
    protected final MutableLiveData<PackagingType> selectedPackagingType;

    protected final MutableLiveData<Double> calculatedTotalWeight;
    protected final MutableLiveData<Double> calculatedTotalVolume;

    /* consignments added for calculation */
    protected List<Consignment> consignmentList;
    protected final MutableLiveData<List<Consignment>> observableConsignmentList;

    /* data for calculation */
    private final MutableLiveData<List<ZoneSettings>> selectedZoneSettingsList;
    private final MutableLiveData<List<Packaging>> selectedPackagingList;
    private final MutableLiveData<Vat> selectedVat;

    private final MutableLiveData<List<PackagingAndPrice>> packagingAndPriceList;
    private final MutableLiveData<CalculationResult> calculationResult;

    private final MutableLiveData<UUID> fetchPackagingDataResult;

    public CalculatorViewModel(final Context context) {
        super(context);

        this.providerRepository = new ProviderRepository(context);
        this.packagingRepository = new PackagingRepository(context);
        this.vatRepository = new VatRepository(context);
        this.zoneRepository = new ZoneRepository(context);

        this.selectedCountryId = new MutableLiveData<>();
        this.srcCountry = new MutableLiveData<>();
        this.srcCountryId = new MutableLiveData<>();
        this.destCountry = new MutableLiveData<>();
        this.destCountryId = new MutableLiveData<>();

        this.selectedProvider = new MutableLiveData<>();
        this.selectedProviderId = new MutableLiveData<>();
        this.providerIdList = new MediatorLiveData<>();

        final List<Long> cargostarProviderList = new ArrayList<>();
        final List<Long> tntProviderList = new ArrayList<>();
        final List<Long> tntAndFedexProviderList = new ArrayList<>();

        cargostarProviderList.add(6L);
        tntProviderList.add(5L);
        tntAndFedexProviderList.add(4L);
        tntAndFedexProviderList.add(5L);

        this.providerIdList.addSource(srcCountry, selectedSrcCountry -> {
            if (destCountry.getValue() != null) {
                //cargostar only
                if (selectedSrcCountry.getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))
                        && destCountry.getValue().getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))) {
                    providerIdList.setValue(cargostarProviderList);
                    selectedCountryId.setValue(selectedSrcCountry.getId());
                }
                //tnt only
                else if (!selectedSrcCountry.getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))
                        && destCountry.getValue().getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))) {
                    providerIdList.setValue(tntProviderList);
                    selectedCountryId.setValue(selectedSrcCountry.getId());
                }
                //tnt & fedex
                else if (selectedSrcCountry.getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))
                        && !destCountry.getValue().getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))) {
                    providerIdList.setValue(tntAndFedexProviderList);
                    selectedCountryId.setValue(destCountryId.getValue());
                }
                //nothing
                else {
                    providerIdList.setValue(null);
                }
            }
            //nothing
            else {
                providerIdList.setValue(null);
            }
        });
        this.providerIdList.addSource(destCountry, selectedDestCountry -> {
            if (srcCountry.getValue() != null) {
                //cargostar only
                if (srcCountry.getValue().getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))
                        && selectedDestCountry.getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))) {
                    providerIdList.setValue(cargostarProviderList);
                    selectedCountryId.setValue(selectedDestCountry.getId());
                }
                //tnt only
                else if (!srcCountry.getValue().getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))
                        && selectedDestCountry.getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))) {
                    providerIdList.setValue(tntProviderList);
                    selectedCountryId.setValue(srcCountryId.getValue());
                }
                //tnt & fedex
                else if (srcCountry.getValue().getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))
                        && !selectedDestCountry.getNameEn().equalsIgnoreCase(context.getString(R.string.uzbekistan))) {
                    providerIdList.setValue(tntAndFedexProviderList);
                    selectedCountryId.setValue(selectedDestCountry.getId());
                }
                //nothing
                else {
                    providerIdList.setValue(null);
                    selectedCountryId.setValue(null);
                }
            }
            //nothing
            else {
                providerIdList.setValue(null);
                selectedCountryId.setValue(null);
            }
        });

        this.selectedType = new MutableLiveData<>();
        this.selectedPackagingType = new MutableLiveData<>();
        this.calculatedTotalWeight = new MutableLiveData<>();
        this.calculatedTotalVolume = new MutableLiveData<>();

        /* consignments added to list for calculation */
        this.consignmentList = new ArrayList<>();
        this.observableConsignmentList = new MutableLiveData<>();

        this.calculationResult = new MutableLiveData<>();
        this.packagingAndPriceList = new MutableLiveData<>();

        /* zones */
        this.selectedZoneSettingsList = new MutableLiveData<>();
        this.selectedPackagingList = new MutableLiveData<>();
        this.selectedVat = new MutableLiveData<>();

        this.fetchPackagingDataResult = new MutableLiveData<>();
    }
    /* getters */

    /* CalculatorFragment content views */
    public LiveData<List<Country>> getCountryList() {
        return locationRepository.selectAllCountries();
    }

    public LiveData<List<Long>> getProviderIdList() {
        return providerIdList;
    }

    public LiveData<List<PackagingType>> getPackagingTypeList() {
        return Transformations.switchMap(selectedType, input -> {
            if (input == 1) {
                return Transformations.switchMap(selectedProviderId, packagingRepository::selectDocPackagingTypesByProviderId);
            }
            if (input == 2) {
                return Transformations.switchMap(selectedProviderId, packagingRepository::selectBoxPackagingTypesByProviderId);
            }
            return null;
        });
    }

    public LiveData<List<Packaging>> getPackagingList() {
        return Transformations.switchMap(selectedProviderId, packagingRepository::selectPackagingListByProviderId);
    }

    public LiveData<List<Consignment>> getConsignmentList() {
        return observableConsignmentList;
    }

    public LiveData<CalculationResult> getCalculationResult() {
        return calculationResult;
    }

    public LiveData<List<PackagingAndPrice>> getPackagingAndPriceList() {
        return packagingAndPriceList;
    }

    public LiveData<Vat> getVat() {
        return vatRepository.selectVat();
    }

    public LiveData<List<ZoneSettings>> getZoneSettingsList() {
        return Transformations.switchMap(selectedProviderId, input -> {
            if (input == 4L) {
                return Transformations.switchMap(selectedCountryId, zoneRepository::selectFedexZoneListByCountryId);
            }
            if (input == 5L) {
                return Transformations.switchMap(selectedCountryId, zoneRepository::selectTntZoneListByCountryId);
            }
            if (input == 6L) {
                return Transformations.switchMap(selectedCountryId, zoneRepository::selectCargostarZoneListByCountryId);
            }
            return null;
        });
    }

    /* setters */
    public void setSrcCountry(final Country country) {
        this.srcCountry.setValue(country);
        this.srcCountryId.setValue(country.getId());
    }

    public void setDestCountry(final Country country) {
        this.destCountry.setValue(country);
        this.destCountryId.setValue(country.getId());
    }

    public void setSelectedProviderId(final long providerId) {
        this.selectedProviderId.setValue(providerId);
        this.providerId = providerId;
    }

    public LiveData<Provider> getSelectedProvider() {
        return Transformations.switchMap(selectedProviderId, providerRepository::selectProviderById);
    }

    public void setSelectedProvider(final Provider provider) {
        this.selectedProvider.setValue(provider);
    }

    public void setSelectedType(final int type) {
        this.selectedType.setValue(type);
        this.type = type;
    }

    public void setSelectedPackagingType(final PackagingType packagingType) {
        this.selectedPackagingType.setValue(packagingType);
    }

    public void setSelectedVat(final Vat vat) {
        this.selectedVat.setValue(vat);
    }

    public void setSelectedPackagingList(final List<Packaging> packagingList) {
        this.selectedPackagingList.setValue(packagingList);
    }

    public void setSelectedZoneSettingsList(final List<ZoneSettings> zoneSettingsList) {
        this.selectedZoneSettingsList.setValue(zoneSettingsList);
    }

    /* adding consignments to list to calculate transportation price */
    public void addConsignment(final long packagingTypeId,
                               final double length,
                               final double width,
                               final double height,
                               final double weight) {
        final int lastIndex = consignmentList.size();
        consignmentList.add(new Consignment(
                lastIndex,
                0L,
                packagingTypeId,
                null,
                null,
                null,
                length,
                width,
                height,
                weight,
                length + "x" + width + "x" + height,
                null));
        observableConsignmentList.setValue(consignmentList);
    }

    public void removeConsignment(final int index) {
        consignmentList.remove(index);
        observableConsignmentList.setValue(consignmentList);
    }

    /* calculate total price */
    public void calculateTotalPrice() {
        if (selectedType.getValue() == null) {
            Log.e(TAG, "calculateTotalPrice(): type is undefined");
            return;
        }
        if (selectedProviderId.getValue() == null) {
            Log.e(TAG, "calculateTotalPrice(): provider is undefined");
            return;
        }
        if (selectedVat.getValue() == null) {
            Log.e(TAG, "calculateTotalPrice(): vat is undefined");
            return;
        }
        if (selectedZoneSettingsList.getValue() == null) {
            Log.e(TAG, "calculateTotalPrice(): zoneSettingsList is undefined");
            return;
        }
        if (selectedZoneSettingsList.getValue().isEmpty()) {
            Log.e(TAG, "calculateTotalPrice(): zoneSettingsList is empty");
            return;
        }
        if (selectedPackagingList.getValue() == null) {
            Log.e(TAG, "calculateTotalPrice(): packagingList is undefined");
            return;
        }
        if (selectedProvider.getValue() == null) {
            Log.e(TAG, "calculateTotalPrice(): wrong selected provider data");
            return;
        }
        double totalVolume = 0.0;
        double totalWeight = 0.0;

        for (final Consignment item : consignmentList) {
            totalWeight += item.getWeight();
            totalVolume += item.getLength() * item.getWidth() * item.getHeight();
        }
        final Map<ZoneSettings, Packaging> zoneSettingsTariffMap = new HashMap<>();

        for (final ZoneSettings zoneSettings : selectedZoneSettingsList.getValue()) {
            for (final Packaging packaging : selectedPackagingList.getValue()) {
                if (packaging.getId() == zoneSettings.getPackagingId()) {
                    final int volumex = packaging.getVolumex();

                    if (volumex > 0) {
                        final double volumexWeight = totalVolume / volumex;

                        if (volumexWeight > totalWeight) {
                            totalWeight = volumexWeight;
                        }
                    }
                    if (totalWeight > zoneSettings.getWeightFrom() && totalWeight <= zoneSettings.getWeightTo()) {
                        zoneSettingsTariffMap.put(zoneSettings, packaging);
                    }
                }
            }
        }
        this.calculatedTotalWeight.setValue(totalWeight);
        this.totalWeight = totalWeight;
        this.calculatedTotalVolume.setValue(totalVolume);
        this.totalVolume = totalVolume;

        final List<PackagingAndPrice> resultList = new ArrayList<>();
        double totalPrice = 0.0;

        for (final ZoneSettings actualZoneSettings : zoneSettingsTariffMap.keySet()) {
            totalPrice = actualZoneSettings.getPriceFrom();
            final Packaging correspondingTariff = zoneSettingsTariffMap.get(actualZoneSettings);

            for (double i = actualZoneSettings.getWeightFrom(); i < totalWeight; i += actualZoneSettings.getWeightStep()) {
                if (actualZoneSettings.getWeightStep() <= 0) {
                    break;
                }
                totalPrice += actualZoneSettings.getPriceStep();
            }
            if (totalPrice > 0) {
                if (correspondingTariff != null) {
                    if (selectedType.getValue() == 2) {
                        totalPrice += correspondingTariff.getParcelFee();
                    }
                }
                totalPrice = totalPrice * (selectedProvider.getValue().getFuel() + 100) / 100;
                totalPrice *= (selectedVat.getValue().getVat() + 100) / 100;
                totalPrice = (double) Math.round(totalPrice*100) / 100;
            }
            if (correspondingTariff != null) {
                resultList.add(new PackagingAndPrice(correspondingTariff, totalPrice));
            }
        }
        packagingAndPriceList.setValue(resultList);
    }

    public void fetchPackagingData() {
        fetchPackagingDataResult.setValue(packagingRepository.fetchPackagingData());
    }

    public void setTotalPrice(final double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalWeight(final double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setTotalVolume(final double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public LiveData<WorkInfo> getFetchPackagingDataResult(final Context context) {
        return Transformations.switchMap(fetchPackagingDataResult, input -> WorkManager.getInstance(context).getWorkInfoByIdLiveData(input));
    }

    private static final String TAG = CalculatorViewModel.class.toString();
}

