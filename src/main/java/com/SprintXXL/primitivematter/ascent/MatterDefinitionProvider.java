package com.SprintXXL.primitivematter.ascent;

import com.SprintXXL.primitivematter.library.devices.definitions.ModDevices;
import com.SprintXXL.primitivematter.library.substances.definitions.ModSubstances;
import com.sprintxxl.ascenthub.definitions.AscentDefinition;
import com.sprintxxl.ascenthub.definitions.AscentDefinitionProvider;
import com.sprintxxl.ascenthub.definitions.DefinitionRegistrar;

import static com.SprintXXL.primitivematter.Reference.MODID;
import static com.sprintxxl.ascenthub.definitions.registry.DefinitionProviderRegistry.registerProvider;

public final class MatterDefinitionProvider implements AscentDefinitionProvider {

    private MatterDefinitionProvider() {}

    public static void initMatterDefinitionProvider() {
        registerProvider(MODID, new MatterDefinitionProvider());
    }

    @Override
    public void registerDefinitions(DefinitionRegistrar<AscentDefinition> registrar) {

        ModSubstances.initSubstanceDefinitions(registrar::register);
        ModDevices.initDeviceDefinitions(registrar::register);
    }
}
