package com.mrbbot.infusio;

public class Reference {
    static final String MOD_ID = "infusio";
    static final String MOD_NAME = "Infusio";
    static final String MOD_VERSION = "1.0";

    static final String CLIENT_PROXY_CLASS = "com.mrbbot.infusio.proxy.ClientProxy";
    static final String SERVER_PROXY_CLASS = "com.mrbbot.infusio.proxy.ServerProxy";

    public enum InfusioItems {
        ACTIVATION_STICK("activationStick", "activation_stick"),
        ACTIVATION_ROD("activationRod", "activation_rod"),
        SCORCHED_DUST("scorchedDust", "scorched_dust");

        private String unlocalizedName;
        private String registryName;

        InfusioItems(String unlocalizedName, String registryName) {
            this.unlocalizedName = unlocalizedName;
            this.registryName = registryName;
        }

        public String getUnlocalizedName() {
            return unlocalizedName;
        }

        public String getRegistryName() {
            return registryName;
        }
    }

    public enum InfusioBlocks {
        SCORCHED_STONE("scorchedStone", "scorched_stone"),
        PEDESTAL("pedestal", "pedestal");

        private String unlocalizedName;
        private String registryName;

        InfusioBlocks(String unlocalizedName, String registryName) {
            this.unlocalizedName = unlocalizedName;
            this.registryName = registryName;
        }

        public String getUnlocalizedName() {
            return unlocalizedName;
        }

        public String getRegistryName() {
            return registryName;
        }
    }
}
