package io.github.rurien.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.stereotype.Component

@Component
@ConfigurationPropertiesScan(basePackages = ["com.github.rurien.common.properties"])
class PropertyConfiguration
