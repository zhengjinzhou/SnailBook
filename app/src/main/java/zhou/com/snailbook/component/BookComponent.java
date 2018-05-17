/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zhou.com.snailbook.component;



import dagger.Component;
import zhou.com.snailbook.ui.activity.BookDetailActivity;
import zhou.com.snailbook.ui.activity.BookDetailReviewFragment;
import zhou.com.snailbook.ui.activity.BookSourceActivity;
import zhou.com.snailbook.ui.activity.BooksByTagActivity;
import zhou.com.snailbook.ui.activity.ReadActivity;
import zhou.com.snailbook.ui.activity.SearchActivity;
import zhou.com.snailbook.ui.activity.SearchByAuthorActivity;
import zhou.com.snailbook.ui.fragment.BookDetailDiscussionFragment;

@Component(dependencies = AppComponent.class)
public interface BookComponent {
    BookDetailActivity inject(BookDetailActivity activity);

    ReadActivity inject(ReadActivity activity);

    BookSourceActivity inject(BookSourceActivity activity);

    BooksByTagActivity inject(BooksByTagActivity activity);

    SearchActivity inject(SearchActivity activity);

    SearchByAuthorActivity inject(SearchByAuthorActivity activity);

    BookDetailReviewFragment inject(BookDetailReviewFragment fragment);

    BookDetailDiscussionFragment inject(BookDetailDiscussionFragment fragment);
}