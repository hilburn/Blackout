package com.hilburn.blackout.handlers.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIBlackoutConfig implements IConfigureNEI
{

    @Override
    public String getName()
    {
        return "Blackout NEI Plugin";
    }

    @Override
    public String getVersion()
    {
        return "v1";
    }

    @Override
    public void loadConfig()
    {
        StellarFabricatorNEIRecipeHandler stellarFabricatorRecipeHandler = new StellarFabricatorNEIRecipeHandler();
        API.registerRecipeHandler(stellarFabricatorRecipeHandler);
        API.registerUsageHandler(stellarFabricatorRecipeHandler);
    }

}