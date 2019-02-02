package com.anou.prototype.core.usecase

import com.anou.prototype.core.db.ModuleEntity

/*******************************************************************************
 * QuickSeries® Publishing inc.
 * <p>
 * Copyright (c) 1992-2017 QuickSeries® Publishing inc.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of QuickSeries®
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with QuickSeries® and QuickSeries's Partners.
 * <p>
 * Created by Anou Chanthavong on 2019-02-02.
 ******************************************************************************/
sealed class ModuleUseCase {
    class SetData(val modules : List<ModuleEntity>): ModuleUseCase()
    class InitializeModule(val module : ModuleEntity): ModuleUseCase()
    class ShowError(val errorMessage : String): ModuleUseCase()
    class ShowSuccess(val successMessage : String): ModuleUseCase()
    class ShowEmpty(val emptyMessage : String): ModuleUseCase()
    object ShowLoading: ModuleUseCase()
    object HideLoading: ModuleUseCase()

}